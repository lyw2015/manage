/*
Navicat MariaDB Data Transfer

Source Database       : manage

Target Server Type    : MariaDB
Target Server Version : 100310
File Encoding         : 65001

Date: 2018-11-27
*/

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `sys_type` varchar(100) NOT NULL,
  `sys_jsondata` varchar(2000) NOT NULL,
  PRIMARY KEY (`sys_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES ('LOGIN_RULE', '{\"saovalidate\":\"true\",\"saonumber\":\"1\",\"lenvalidate\":\"true\",\"errornumber\":\"3\",\"againlogin\":\"30\"}');

-- ----------------------------
-- Table structure for t_sys_dictionaries
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dictionaries`;
CREATE TABLE `t_sys_dictionaries` (
  `d_id` varchar(64) NOT NULL,
  `d_name` varchar(100) NOT NULL,
  `d_identification` varchar(100) NOT NULL,
  `d_description` varchar(1000) DEFAULT NULL,
  `d_update_time` varchar(32) DEFAULT NULL,
  `d_status` int(11) DEFAULT 1,
  PRIMARY KEY (`d_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_dictionaries
-- ----------------------------
INSERT INTO `t_sys_dictionaries` VALUES ('34F28BDC3ECF495D8FE358248CACCD71', '用户类型', 'user_type', '用户类型', '2018-11-15 15:53:36', '1');
INSERT INTO `t_sys_dictionaries` VALUES ('3501DEBABC8444B1B3BDAF223F79CCE8', '机构类型', 'dept_type', '机构类型', '2018-11-15 15:38:22', '1');

-- ----------------------------
-- Table structure for t_sys_dictionaries_item
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dictionaries_item`;
CREATE TABLE `t_sys_dictionaries_item` (
  `di_id` varchar(64) NOT NULL,
  `d_id` varchar(64) NOT NULL,
  `di_name` varchar(100) NOT NULL,
  `di_value` varchar(100) NOT NULL,
  `di_status` int(11) DEFAULT 1,
  PRIMARY KEY (`di_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_dictionaries_item
-- ----------------------------
INSERT INTO `t_sys_dictionaries_item` VALUES ('7613E8A5FA0F4DF988270C9C7808E6AA', '3501DEBABC8444B1B3BDAF223F79CCE8', '集团', '集团', '1');
INSERT INTO `t_sys_dictionaries_item` VALUES ('8DD1C6838FD54450973FA4028450B9E0', '3501DEBABC8444B1B3BDAF223F79CCE8', '组', '组', '1');
INSERT INTO `t_sys_dictionaries_item` VALUES ('D3FE004AA14B48429915E2AC41271D58', '3501DEBABC8444B1B3BDAF223F79CCE8', '公司', '公司', '1');
INSERT INTO `t_sys_dictionaries_item` VALUES ('D448BE2057B14448A2382E5B52C38B8B', '3501DEBABC8444B1B3BDAF223F79CCE8', '部门', '部门', '1');

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `m_id` varchar(64) NOT NULL,
  `m_parent_id` varchar(64) DEFAULT NULL,
  `m_name` varchar(100) NOT NULL,
  `m_icon` varchar(30) DEFAULT NULL,
  `m_sort` int(11) NOT NULL,
  `m_url` varchar(100) DEFAULT NULL,
  `m_description` varchar(1000) DEFAULT NULL,
  `m_status` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES ('08F63797F0CA46299268C2816FB74AEB', '9D8431FE7288481CA792CA72F5ED9F9A', '404 Error', 'fa fa-eye-slash', '200', 'pages/system/404.html', '找不到页面时跳转的页面', '1');
INSERT INTO `t_sys_menu` VALUES ('0ADAE38220754050A2FD8BC4E77FAB6A', 'D53DE23EDD544D80B0763BA21BDA2C19', '访客统计', 'fa fa-eye', '500', 'pages/monitor/guest.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('0B3B79641F4B4ACC8BE801EC41FC3006', '5DC2DBA1D08F4BED9795B8A855ABD961', '菜单管理', 'fa fa-tree', '200', 'pages/system/menu.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('18582F8EBAF24618BA8250CC5819E9CE', '9D8431FE7288481CA792CA72F5ED9F9A', '500 Error', 'fa fa-frown-o', '100', 'pages/system/500.html', '系统发生异常时跳转的页面', '1');
INSERT INTO `t_sys_menu` VALUES ('242431D8DB5B492E964A0A4FCE91B38A', 'D53DE23EDD544D80B0763BA21BDA2C19', '在线用户', 'fa fa-users', '600', 'pages/monitor/online-user.html', '展示在线用户信息', '1');
INSERT INTO `t_sys_menu` VALUES ('3804BFD84B0441EFA0B99C85622C7733', '444CB011FCB04E5B9C7D710117E2A1BA', '机构管理', 'fa fa-bars', '300', 'pages/organization/mechanisms.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('40B77FA0E56244569B50A2DD2EBCE34C', '5DC2DBA1D08F4BED9795B8A855ABD961', '基础配置', 'fa fa-asterisk', '400', 'pages/system/setting.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('40CBF110E96945EC8C6696D6BFB09E72', 'D53DE23EDD544D80B0763BA21BDA2C19', '数据监控', 'fa fa-database', '300', 'monitor/druid', '数据监控', '1');
INSERT INTO `t_sys_menu` VALUES ('444CB011FCB04E5B9C7D710117E2A1BA', '', '组织架构', 'fa fa-arrows', '400', '', '', '1');
INSERT INTO `t_sys_menu` VALUES ('4F9BC8DFB3554E5587CB0ECB12087C46', 'D53DE23EDD544D80B0763BA21BDA2C19', '访问日志', 'fa fa-list', '100', 'pages/monitor/accessing-log.html', '展示系统的操作日志', '1');
INSERT INTO `t_sys_menu` VALUES ('51646DCE7D7F42C4B58378C4E1CD02E9', '444CB011FCB04E5B9C7D710117E2A1BA', '用户管理', 'fa fa-users', '100', 'pages/organization/users.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('5DC2DBA1D08F4BED9795B8A855ABD961', '', '系统设置', 'fa fa-cogs', '300', '', '', '1');
INSERT INTO `t_sys_menu` VALUES ('660A7FD98FCB4B0FAE515853747FDEBF', '444CB011FCB04E5B9C7D710117E2A1BA', '角色管理', 'fa fa-object-group', '200', 'pages/organization/roles.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('8BCA0A7B231F4F16A77CEC686F787D2B', 'D53DE23EDD544D80B0763BA21BDA2C19', '缓存监控', 'fa fa-cloud', '400', 'pages/monitor/cache.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('939B2C62A4E74F67A8DA844A6DAD0138', '5DC2DBA1D08F4BED9795B8A855ABD961', '字典管理', 'fa fa-book', '300', 'pages/system/dictionaries.html', '', '1');
INSERT INTO `t_sys_menu` VALUES ('9D8431FE7288481CA792CA72F5ED9F9A', '', '系统异常', 'fa fa-close', '100', '', '', '1');
INSERT INTO `t_sys_menu` VALUES ('C2AA3AE6B81B486A8B8E0F56C7100F23', '', '页面模板', 'fa fa-pagelines', '500', 'pages/template.html', '新增页面时的页面模板', '1');
INSERT INTO `t_sys_menu` VALUES ('D53DE23EDD544D80B0763BA21BDA2C19', '', '系统监控', 'fa fa-dashboard', '200', '', '系统监控', '1');
INSERT INTO `t_sys_menu` VALUES ('F4B43298787E4D8A9D189BA59E985B8B', 'D53DE23EDD544D80B0763BA21BDA2C19', '服务器监控', 'fa fa-tv', '200', 'monitor/server', '', '1');

-- ----------------------------
-- Table structure for t_sys_monitor_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_monitor_log`;
CREATE TABLE `t_sys_monitor_log` (
  `ml_id` varchar(64) NOT NULL,
  `client_ip` varchar(15) NOT NULL,
  `operating_system_name` varchar(100) NOT NULL,
  `browser` varchar(100) DEFAULT NULL,
  `ml_agent` varchar(500) DEFAULT NULL,
  `request_uri` varchar(100) NOT NULL,
  `request_method` varchar(100) NOT NULL,
  `request_parameter` varchar(1000) DEFAULT NULL,
  `operation_time` varchar(26) NOT NULL,
  `operation_username` varchar(100) DEFAULT NULL,
  `response_time` varchar(100) NOT NULL,
  `is_error` varchar(5) NOT NULL,
  `error_message` longtext DEFAULT NULL,
  `operation_description` varchar(2000) DEFAULT NULL,
  `device_type` varchar(30) DEFAULT NULL,
  `group_name` varchar(30) DEFAULT NULL,
  `user_account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ml_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_monitor_log
-- ----------------------------
INSERT INTO `t_sys_monitor_log` VALUES ('00181BF5ECED4E9488B55A04BB395DB0', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/getRoot', 'GET', 'order=asc&offset=0&limit=10&_=1541919465745', '2018-11-11 14:57:45', '超级管理员', '58毫秒', 'false', null, '菜单管理/查看菜单列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('00251F3AD85745DCBFB77AF2E05B281D', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-10 12:50:41', '超级管理员', '244毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('004A26BEE6EA427AB7E38780943F23DA', '192.168.43.55', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dictionaries/getAllDictionaries', 'GET', 'order=asc&_=1542267394380', '2018-11-15 15:37:26', '超级管理员', '207毫秒', 'false', null, '字典管理/查看数据字典', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('004B27BEC0384F3191EA82DE0C1F240A', '192.168.0.100', 'Windows 10', 'Microsoft Edge', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-08 18:05:09', '超级管理员', '177毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0055273793E84896958D18E02EA5C410', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/queryLog', 'GET', 'order=desc&offset=0&limit=10&pageSize=10&pageNum=1&operationDescription=服务器监控&operationUsername=&clientIp=&operatingSystemName=&browser=&requestURI=&requestMethod=&operationTime=2018-10-30+00:00+-+2018-10-30+23:59&isError=&_=1540869378141', '2018-10-30 11:17:14', '管理员', '84毫秒', 'false', null, '监控管理/查询日志', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0063BBC7A9C64494B106B31FCCEA7069', '192.168.0.100', 'Windows 10', 'Microsoft Edge', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134', '/manage/logout', 'GET', '', '2018-11-08 21:09:48', '超级管理员', '25毫秒', 'false', null, '登录登出/用户登出', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('006466D83DA34E4791D8CD65D9178F0A', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dictionaries/updateDictionariesItemStatus', 'POST', 'id=37E0CFFD5A2B42FDB9DC0AE8F3D45176', '2018-11-26 10:15:24', '超级管理员', '257毫秒', 'false', null, '字典管理/删除字典项', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0075010C883E4009B785EE043718B3BA', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/personal/getUserInfo', 'GET', '', '2018-11-04 04:35:33', '管理员', '87毫秒', 'false', null, '个人中心/查看个人信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0089B757D6C64D989C2E5A3F59EF0ABE', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/detailLog', 'GET', 'id=E3A7320962764E10811E575A4E7BDE26', '2018-11-09 18:00:22', '超级管理员', '503毫秒', 'false', null, '监控管理/查看日志详细信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('008BA0F30E2C4B91BEE50E37450D6939', '58.16.58.40', 'Mac OS X', 'Chrome', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/system/getConfigs', 'GET', '', '2018-11-12 17:39:59', '超级管理员', '78毫秒', 'false', null, '查看系统配置', 'Computer', 'Mac OS X', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('008D47842D6E437CAF6F91EC0E8B5CDE', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/queryLog', 'GET', 'pageSize=10&pageNum=1&_=1540865450530', '2018-10-30 10:10:50', '管理员', '93毫秒', 'false', null, '监控管理/查询日志', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('00A76AEB872641909F370CEA1D652C8A', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/system/getConfigs', 'GET', '', '2018-11-07 12:29:48', '超级管理员', '62毫秒', 'false', null, '查看系统配置', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('00AED6B10AA24FCCA24BA62C0359CAC9', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/queryLog', 'GET', 'pageSize=10&pageNum=1&_=1540866310797', '2018-10-30 10:25:11', '管理员', '89毫秒', 'false', null, '监控管理/查询日志', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('00C90711AAF742859689C4246365571E', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/organization/getOrganizationById', 'GET', 'id=100013', '2018-11-27 13:28:40', '超级管理员', '83毫秒', 'false', null, '机构管理/查看机构详情', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('00EA27E7FE4B470BA2DD8DC33E3D6AD6', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/onlineUser', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&operatingSystemName=&browser=&loginTime=2018-11-01 00:00 - 2018-11-01 23:59&_=1541045194118', '2018-11-01 12:06:34', '管理员', '112毫秒', 'false', null, '监控管理/查看在线用户', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('00FE2EC9644F45C8A9BFE822D7B52BD5', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-05 21:16:31', '超级管理员', '851毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01035790A69149F2BAC35E4ACD76C6C5', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/detailLog', 'GET', 'id=C6F5B0FABAFB48B1B63A9EDF0CF93BCC', '2018-10-31 16:34:56', '管理员', '547毫秒', 'false', null, '监控管理/查看日志详细信息', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01096E65C57A47B29C1A5243E9D190EE', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/getRoot', 'GET', 'pageSize=10&pageNum=1&_=1541989399754', '2018-11-12 10:23:19', '超级管理员', '98毫秒', 'false', null, '菜单管理/查看菜单列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('010C3B34F437467583E4C360BEDEEABC', '192.168.43.55', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/organization/searchOrganization', 'GET', 'order=asc&_=1543216838564', '2018-11-26 15:44:39', '超级管理员', '380毫秒', 'false', null, '机构管理/查看机构列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0113F82211064C8EA49ABD005648E8BA', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/getRoot', 'GET', 'pageSize=10&pageNum=1&_=1541931405547', '2018-11-11 18:16:45', '超级管理员', '89毫秒', 'false', null, '菜单管理/查看菜单列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('011E79F9CCC8424C90C3DD3A82E82756', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-16 16:06:36', '超级管理员', '279毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01206A223CA343CFB68838D21061D950', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/onlineUser', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&operatingSystemName=&browser=&loginTime=&_=1541176908144', '2018-11-03 00:41:51', '管理员', '145毫秒', 'false', null, '监控管理/查看在线用户', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01318B43F36F4A04A28E1831D964050A', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/detailLog', 'GET', 'id=7DA87EE3ADCC494D80F912BE46458D1D', '2018-11-03 01:45:06', '管理员', '109毫秒', 'false', null, '监控管理/查看日志详细信息', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0133C3B9BCA74183847E77FBB467566C', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/getRoot', 'GET', 'pageSize=10&pageNum=1&_=1541988331214', '2018-11-12 10:05:31', '超级管理员', '96毫秒', 'false', null, '菜单管理/查看菜单列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01598583CD684F87804B7ED433C24739', '192.168.0.100', 'Windows 10', 'Microsoft Edge', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134', '/monitor/onlineUser', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&deviceType=&loginTime=2018-11-08 00:00 - 2018-11-08 23:59&_=1541679741134', '2018-11-08 20:22:21', '超级管理员', '108毫秒', 'false', null, '监控管理/查看在线用户', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('015D6B3261FD49968775D93790407AD6', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/queryLog', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&operationDescription=&operationUsername=&clientIp=&operatingSystemName=&browser=&requestURI=&requestMethod=&operationTime=2018-10-31 00:00 - 2018-10-31 23:59&isError=&_=1540974825801', '2018-10-31 16:33:46', '管理员', '176毫秒', 'false', null, '监控管理/查询日志', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01620FB1C8E046B8A3C4DF5F07F72E9D', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/getCacheKeysByCond', 'GET', 'key=&_=1541827362233', '2018-11-10 13:22:42', '超级管理员', '51毫秒', 'false', null, '监控管理/查看缓存', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01670CB37795498E89BA83DE1DD243C2', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/onlineUser', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&deviceType=Computer&loginTime=2018-11-03 00:00 - 2018-11-03 23:59&_=1541230341591', '2018-11-03 15:33:12', '管理员', '57毫秒', 'false', null, '监控管理/查看在线用户', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01685D3C1B0E4239AA502CE768393DA7', '192.168.0.100', 'Windows 10', 'Internet Explorer 11', 'Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-08 16:18:50', '超级管理员', '237毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('016972B49ACE4A3DB20515E73902C351', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/organization/getOrganizationById', 'GET', 'id=100004', '2018-11-27 12:32:24', '超级管理员', '77毫秒', 'false', null, '机构管理/查看机构详情', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('017792A7BD6840B8AB6E2B4F7E8007B9', '192.168.43.55', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/menu/getRoot', 'GET', 'order=asc&_=1542252770259', '2018-11-15 11:32:50', '超级管理员', '214毫秒', 'false', null, '菜单管理/查看菜单列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01839517FF8A48C3BEBF1FCB3F82DAC2', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/system/testConnection', 'POST', 'host=smtp.qq.com&port=587&username=1319404727@qq.com&password=******', '2018-11-07 12:20:25', '超级管理员', '1秒16毫秒', 'false', null, '测试邮件服务', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01ACEC9DFD2948BCA5C240041E7BE5E3', '192.168.43.55', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/dictionaries/saveDictionaries', 'POST', '', '2018-11-15 15:19:02', '超级管理员', '1毫秒', 'false', null, '字典管理/保存数据字典', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('01DACD6CB0D64EE4A507941366D4B414', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/guestRecord', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&operatingSystemName=&browser=&loginTime=2018-11-03 00:00 - 2018-11-03 23:59&_=1541179843289', '2018-11-03 01:30:43', '管理员', '131毫秒', 'false', null, '监控管理/查看访客记录', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('02243E5236044A379FF0C4CC8169C903', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/getMenuById', 'GET', 'id=5DC2DBA1D08F4BED9795B8A855ABD961', '2018-11-12 10:18:02', '超级管理员', '65毫秒', 'false', null, '菜单管理/查看菜单详细信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0234FA18675645C8B7EBAD37898DFF67', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/system/getConfigs', 'GET', '', '2018-11-06 19:41:17', '超级管理员', '64毫秒', 'false', null, '查看系统配置', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0239659C576243EE8EF17FFC233CE158', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/getRoot', 'GET', 'pageSize=10&pageNum=1&_=1542003079841', '2018-11-12 14:11:25', '超级管理员', '69毫秒', 'false', null, '菜单管理/查看菜单列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0253A981B88F48EE8B9291C4A9231950', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/organization/searchOrganization', 'GET', 'type=&name=&personInCharge=&address=&officePhone=&_=1543296308464', '2018-11-27 13:28:31', '超级管理员', '76毫秒', 'false', null, '机构管理/查看机构列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('02618AD465DF4F4BA77CEC70620664CF', '192.168.0.100', 'Windows 10', 'Microsoft Edge', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134', '/manage/login', 'POST', 'account=admin&password=******&rememberMe=true', '2018-11-08 22:11:25', '超级管理员', '1秒192毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('028F797F2D8E4D34B30FBA1265950DF0', '192.168.0.100', 'Windows 10', 'Microsoft Edge', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134', '/manage/logout', 'GET', '', '2018-11-08 17:06:46', '超级管理员', '6毫秒', 'false', null, '登录登出/用户登出', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('02966D06136245E399C3552E7AE2CFD2', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/onlineUser', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&operatingSystemName=&browser=&loginTime=2018-10-31 00:00 - 2018-10-31 23:59&_=1540982642355', '2018-10-31 18:44:02', '管理员', '135毫秒', 'false', null, '监控管理/查看在线用户', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('029927F0E06243D2A5F3F7487711C325', '192.168.0.100', 'Windows 10', 'Microsoft Edge', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134', '/monitor/onlineUser', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&deviceType=&loginTime=2018-11-08 00:00 - 2018-11-08 23:59&_=1541681990342', '2018-11-08 20:59:51', '超级管理员', '615毫秒', 'false', null, '监控管理/查看在线用户', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('029E742AF0EB466F958B4E40F0ACC8A9', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/getRoot', 'GET', 'pageSize=10&pageNum=1&_=1541941570900', '2018-11-11 21:06:11', '超级管理员', '83毫秒', 'false', null, '菜单管理/查看菜单列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('02AA82643A5749599EB5B0471C0BA0ED', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/personal/getUserInfo', 'GET', '', '2018-11-04 19:07:32', '超级管理员', '134毫秒', 'false', null, '个人中心/查看个人信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('02F3A3277DE54DDD940DD5E15D680472', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-01 10:08:38', '管理员', '1秒160毫秒', 'false', null, '登录登出/用户登录', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('031B05227C1246EBB5DEE0B42ECA4583', '192.168.0.100', 'Windows 10', 'Microsoft Edge', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134', '/manage/logout', 'GET', '', '2018-11-08 18:25:18', '超级管理员', '7毫秒', 'false', null, '登录登出/用户登出', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('031DB2AD1D494E2897AA1DC5CC7781C5', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=123456', '2018-10-31 17:52:21', '管理员', '186毫秒', 'false', null, '登录登出/用户登录', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('032C8BB0C8274B4A884570B14696ABD5', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/guestRecord', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&operatingSystemName=&browser=&loginTime=2018-11-03 00:00 - 2018-11-03 23:59&_=1541230484473', '2018-11-03 15:34:44', '管理员', '73毫秒', 'false', null, '监控管理/查看访客记录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0332772929854FC3AE879F13F7E375FB', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-09 09:48:27', '超级管理员', '1秒188毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('03381D5C6D7441AEA848B9CFE9DF583A', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/personal/getUserInfo', 'GET', '', '2018-11-03 20:10:29', '超级管理员', '2毫秒', 'false', null, '个人中心/查看个人信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('033BBFFA25344D639B2B620BBAA4C8D6', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/logout', 'GET', '', '2018-11-12 15:48:36', '超级管理员', '84毫秒', 'false', null, '登录登出/用户登出', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('033F9EE00009462E9950BDCFA712B6F6', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/system/getConfigs', 'GET', '', '2018-11-06 20:40:45', '超级管理员', '51毫秒', 'false', null, '查看系统配置', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('03409E443DB44C5194AEDBA964EDE65E', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******&rememberMe=true', '2018-11-10 12:55:02', '超级管理员', '193毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0375FDF530614DF7ABC68A6D23081B1E', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-02 23:14:45', '管理员', '822毫秒', 'false', null, '登录登出/用户登录', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('03806BE0FDE5455DB3447F7C41370EC7', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-12 13:20:10', '超级管理员', '545毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('03851FB0AA9D4C55A066FBCD9CE3ED5C', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/system/getConfigs', 'GET', '', '2018-11-06 21:01:00', '超级管理员', '55毫秒', 'false', null, '查看系统配置', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0387130BFDD740709B1960E3D3A12C80', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******&rememberMe=true', '2018-11-10 15:20:16', '超级管理员', '391毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('03E997CE54944F6EAB18B6401ABFEB1E', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/organization/searchOrganization', 'GET', 'order=asc&_=1542361976415', '2018-11-16 17:52:57', '超级管理员', '206毫秒', 'false', null, '机构管理/查看机构列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('03EB5D15D55C4382829784E7DE7A49BF', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/guestRecord', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&operatingSystemName=&browser=&loginTime=2018-11-03 00:00 - 2018-11-03 23:59&_=1541242378576', '2018-11-03 18:52:58', '管理员', '76毫秒', 'false', null, '监控管理/查看访客记录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('04106397AF9C4746A1EFE9B99CE970A8', '192.168.43.55', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/monitor/queryLog', 'GET', 'pageSize=10&pageNum=1&operationDescription=&operationUsername=&clientIp=&operatingSystemName=&browser=&requestURI=&requestMethod=&operationTime=2018-11-15 00:00 - 2018-11-15 23:59&isError=&_=1542274965257', '2018-11-15 17:42:45', '超级管理员', '370毫秒', 'false', null, '监控管理/查询日志', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('041175D637A94D11BFF64381318ACBA3', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=123456', '2018-10-31 17:53:12', '管理员', '81毫秒', 'false', null, '登录登出/用户登录', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('043A19611E994F1BB3B90A0044DE95C1', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=123456', '2018-10-31 19:38:17', '管理员', '153毫秒', 'false', null, '登录登出/用户登录', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('043C8A33102946F49BA9079BF53B1C64', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/detailLog', 'GET', 'id=A8184FC782B74F659126DAA8A8C8EEA4', '2018-10-30 15:18:49', '管理员', '98毫秒', 'false', null, '监控管理/查看日志详细信息', null, null, 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0451129FA75A494EB8A997E4B91EF545', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/getCacheKeysByCond', 'GET', 'key=*&_=1541760801306', '2018-11-09 18:53:21', '超级管理员', '3毫秒', 'false', null, '监控管理/缓存监控/查看缓存列表', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('045CCBE76E3C4A6FB3CCD8D419A48ABD', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/manage/login', 'POST', 'account=admin&password=******', '2018-11-10 15:02:31', '超级管理员', '327毫秒', 'false', null, '登录登出/用户登录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0481ADBD796F423EA411EA7BA009C55C', '192.168.43.55', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/personal/updateUserInfo', 'POST', 'username=超级管理员&sex=男&mailbox=1319404727@qq.com&phone=', '2018-11-15 16:32:14', '超级管理员', '381毫秒', 'false', null, '个人中心/修改用户信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('0483F2DB6C1D48E483E23CAD49F9E58D', '192.168.43.55', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36', '/menu/getMenuById', 'GET', 'id=C2AA3AE6B81B486A8B8E0F56C7100F23', '2018-11-15 11:38:29', '超级管理员', '218毫秒', 'false', null, '菜单管理/查看菜单详细信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('049298E64D8B43E08FD70BE523C93CF4', '183.6.159.231', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/guestRecord', 'GET', 'pageSize=10&pageNum=1&userName=&clientIp=&operatingSystemName=&browser=&loginTime=2018-11-12 00:00 - 2018-11-12 23:59&_=1542015881763', '2018-11-12 17:45:22', '超级管理员', '94毫秒', 'false', null, '监控管理/查看访客记录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('049E195ED61640B8AD2AD4DBBCDD2516', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/guestRecord', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=&clientIp=&operatingSystemName=&browser=&loginTime=2018-11-10 00:00 - 2018-11-10 23:59&_=1541834439371', '2018-11-10 15:21:02', '超级管理员', '101毫秒', 'false', null, '监控管理/查看访客记录', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('04A8C1255A52420B9013FBE4F542119D', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/onlineUser', 'GET', 'pageSize=10&pageNum=1&userName=&clientIp=&deviceType=&loginTime=2018-11-12 00:00 - 2018-11-12 23:59&_=1542008898158', '2018-11-12 15:48:18', '超级管理员', '442毫秒', 'false', null, '监控管理/查看在线用户', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('04ABEFC27702457D8C028E9C8888D9FA', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/detailLog', 'GET', 'id=A86FCA76BE61411D9570596C3D78E529', '2018-11-03 23:39:15', '超级管理员', '53毫秒', 'false', null, '监控管理/查看日志详细信息', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('04B095B079704E0B8F62EA9D300AA838', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/menu/updateMenuInfo', 'POST', 'id=C2AA3AE6B81B486A8B8E0F56C7100F23&isChildren=false&parentName=&parentId=&name=页面模板&url=pages/template.html&icon=fa fa-pagelines&sort=4&description=新增页面时的页面模板', '2018-11-12 10:26:56', '超级管理员', '88毫秒', 'false', null, '菜单管理/修改菜单', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('04B130D2D8B44794956978FF6CDB37B0', '192.168.0.100', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/system/testConnection', 'POST', 'host=smtp.qq.com&port=587&username=1319404727@qq.com&password=******', '2018-11-07 13:23:38', '超级管理员', '1分73毫秒', 'false', null, '测试邮件服务', 'Computer', 'Windows', 'admin');
INSERT INTO `t_sys_monitor_log` VALUES ('04C68260CD0C4584939A930FCC8D855E', '192.168.1.101', 'Windows 10', 'Chrome', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36', '/monitor/guestRecord', 'GET', 'pageSize=10&pageNum=1&sortOrder=desc&userName=管理&clientIp=&operatingSystemName=&browser=&loginTime=&_=1541244733390', '2018-11-03 19:32:31', '管理员', '76毫秒', 'false', null, '监控管理/查看访客记录', 'Computer', 'Windows', 'admin');

-- ----------------------------
-- Table structure for t_sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_organization`;
CREATE TABLE `t_sys_organization` (
  `o_id` int(11) NOT NULL AUTO_INCREMENT,
  `o_parent_id` int(11) DEFAULT 0,
  `o_type` varchar(100) NOT NULL,
  `o_name` varchar(100) NOT NULL,
  `o_full_name` varchar(100) NOT NULL,
  `o_update_time` varchar(32) DEFAULT NULL,
  `o_person_in_charge` varchar(10) DEFAULT NULL,
  `o_office_phone` varchar(30) DEFAULT NULL,
  `o_address` varchar(200) DEFAULT NULL,
  `o_postal_code` varchar(20) DEFAULT NULL,
  `o_mailbox` varchar(50) DEFAULT NULL,
  `o_description` varchar(1000) DEFAULT NULL,
  `o_status` int(11) DEFAULT 1,
  PRIMARY KEY (`o_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100025 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_organization
-- ----------------------------
INSERT INTO `t_sys_organization` VALUES ('100001', '100017', '部门', '技术中心', '技术中心', '2018-11-27 12:45:32', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100004', '100014', '部门', '董事会', '董事会', '2018-11-27 12:32:38', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100005', '100022', '组', 'Web开发组', 'Web开发组', '2018-11-27 12:47:27', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100013', '100022', '组', '后端开发组', '后端开发组', '2018-11-27 14:26:00', '张三', '938-236-5632', '广东省广州市天河区华南理工大学五山校区', '440153453', '1319404727@qq.com', '机构介绍', '1');
INSERT INTO `t_sys_organization` VALUES ('100014', '0', '公司', 'xx科技', '广州xx科技有限公司', '2018-11-27 12:29:53', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100015', '100017', '部门', '财务部', '财务部', '2018-11-27 12:45:08', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100016', '100004', '部门', '董事秘书会', '董事秘书会', '2018-11-27 12:40:21', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100017', '100004', '部门', '总经办', '总经办', '2018-11-27 12:40:58', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100018', '100017', '部门', '营销中心', '营销中心', '2018-11-27 12:43:03', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100019', '100018', '部门', '信息资源部', '信息资源部', '2018-11-27 12:43:38', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100020', '100018', '部门', '软件销售部', '软件销售部', '2018-11-27 12:44:02', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100021', '100017', '部门', '人事行政部', '人事行政部', '2018-11-27 12:45:18', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100022', '100001', '部门', '技术部', '技术部', '2018-11-27 12:47:07', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100023', '100001', '部门', '研发部', '研发部', '2018-11-27 12:48:13', '', '', '', '', '', '', '1');
INSERT INTO `t_sys_organization` VALUES ('100024', '100022', '组', '测试组', '测试组', '2018-11-27 12:49:37', '', '', '', '', '', '', '1');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `user_id` varchar(64) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_password` varchar(64) NOT NULL,
  `create_date` varchar(30) DEFAULT NULL,
  `user_account` varchar(50) NOT NULL,
  `user_locked` int(11) NOT NULL DEFAULT 0,
  `user_head_img` varchar(500) DEFAULT NULL,
  `user_mailbox` varchar(50) DEFAULT NULL,
  `user_phone` varchar(30) DEFAULT NULL,
  `user_sex` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('048C50FDCE9647D58AFF479C4163726A', '超级管理员', '038bdaf98f2037b31f1e75b5b4c9b26e', '2018-10-31 20:24:04', 'admin', '0', null, '1319404727@qq.com', '', '男');
