package tech.hongjian.blog.utils;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.WireFeedOutput;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import tech.hongjian.blog.consts.BlogConsts;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.frm.exception.ServiceException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BlogUtils {
    /**
     * 匹配邮箱正则
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    public static boolean isEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static String encryptPassword(String username, String password) {
        return DigestUtils.sha1Hex(username + password);
    }

    public static int adjustPageSize(int count) {
        return count < 0 || count > BlogConsts.DEFAULT_PAGE_SIZE ?
                BlogConsts.DEFAULT_PAGE_SIZE : count;
    }

    public static boolean isInstalled() {
        String path = "install.lock";
        File lockFile = new File(path);
        return lockFile.exists();
    }

    public static void createLockFile() {
        String path = "install.lock";
        File lockFile = new File(path);
        try {
            lockFile.createNewFile();
        } catch (IOException e) {
            String msg =
                    "Failed to create the install lock file. Caused by " + e.getMessage();
            throw new ServiceException(msg);
        }
    }


    public static String getFileKey(String name) {
        String dir = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                        "yyyy/MM"));
        if (!Files.exists(Paths.get("upload/" + dir))) {
            new File("upload/" + dir).mkdirs();
        }
        return dir + "/" + getUUID() + "." + fileExt(name);
    }

    public static String getFilePath(String name) {
        return Paths.get("upload/", name).toString();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String fileExt(String filename) {
        int index;
        if (StringUtils.isBlank(filename) || (index = filename.lastIndexOf(".")) == -1 || index == filename.length() - 1) {
            return "";
        }
        return filename.substring(index + 1);
    }

    public static String emoji(String content) {
        return EmojiParser.parseToUnicode(content);
    }

    /**
     * 提取html中的文字
     *
     * @param html
     * @return
     */
    public static String htmlToText(String html) {
        if (StringUtils.isNotBlank(html)) {
            return html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
        }
        return "";
    }

    public static String mdToHtml(String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return "";
        }

        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);
        content = emoji(content);

        // 支持网易云音乐输出
        if (content.contains("[mp3:")) {
            content = content.replaceAll("\\[mp3:(\\d+)\\]", "<iframe frameborder=\"no" +
                    "\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" width=350 " +
                    "height=106 src=\"//music.163" +
                    ".com/outchain/player?type=2&id=$1&auto=0&height=88\"></iframe>");
        }
        // 支持gist代码输出
        if (content.contains("https://gist.github.com/")) {
            content = content.replaceAll("&lt;script src=\"https://gist.github.com/" +
                    "(\\w+)/(\\w+)\\.js\">&lt;/script>", "<script src=\"https://gist" +
                    ".github.com/$1/$2\\.js\"></script>");
        }

        return content;
    }

    public static int addOne(Integer base) {
        if (base == null) {
            return 1;
        }
        return base + 1;
    }

    /**
     * 获取RSS输出
     *
     * @param articles
     * @return
     * @throws FeedException
     */
    public static String getRssXml(java.util.List<Content> articles, String siteUrl,
                                   String siteTitle, String siteDesc) throws FeedException {
        Channel channel = new Channel("rss_2.0");
        channel.setTitle(siteTitle);
        channel.setLink(siteUrl);
        channel.setDescription(siteDesc);
        channel.setLanguage("zh-CN");
        List<Item> items = new ArrayList<>();
        articles.forEach(post -> {
            Item item = new Item();
            item.setTitle(post.getTitle());
            com.sun.syndication.feed.rss.Content content =
                    new com.sun.syndication.feed.rss.Content();
            String value = mdToHtml(post.getContent());

            char[] xmlChar = value.toCharArray();
            for (int i = 0; i < xmlChar.length; ++i) {
                if (xmlChar[i] > 0xFFFD) {
                    //直接替换掉0xb
                    xmlChar[i] = ' ';
                } else if (xmlChar[i] < 0x20 && xmlChar[i] != 't' & xmlChar[i] != 'n' & xmlChar[i] != 'r') {
                    //直接替换掉0xb
                    xmlChar[i] = ' ';
                }
            }

            value = new String(xmlChar);

            content.setValue(value);
            item.setContent(content);
            item.setLink(siteUrl + "/article/" + (StringUtils.isBlank(post.getSlug()) ?
                    post.getId() : post.getSlug()));
            item.setPubDate(post.getCreated());
            items.add(item);
        });
        channel.setItems(items);
        WireFeedOutput out = new WireFeedOutput();
        return out.outputString(channel);
    }
}
