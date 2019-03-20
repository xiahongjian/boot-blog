INSERT INTO `meta` (`id`, `name`, `slug`, `type`, `description`, `sort`, `parent`)
VALUES (1, '默认分类', NULL, 'category', NULL, 0, 0);


INSERT INTO `option` (name, value, description) VALUES ('site_title', 'Tale博客系统', '');
INSERT INTO `option` (name, value, description) VALUES ('social_weibo', '', NULL);
INSERT INTO `option` (name, value, description) VALUES ('social_zhihu', '', NULL);
INSERT INTO `option` (name, value, description) VALUES ('social_github', '', NULL);
INSERT INTO `option` (name, value, description) VALUES ('social_twitter', '', NULL);
INSERT INTO `option` (name, value, description) VALUES ('allow_install', '0', '是否允许重新安装博客');
INSERT INTO `option` (name, value, description) VALUES ('site_theme', 'default', NULL);
INSERT INTO `option` (name, value, description) VALUES ('site_keywords', '博客系统,Blade框架,Tale', NULL);
INSERT INTO `option` (name, value, description) VALUES ('site_description', '博客系统,Blade框架,Tale', NULL);