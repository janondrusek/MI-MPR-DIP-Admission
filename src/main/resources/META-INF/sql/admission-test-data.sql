use admission;

INSERT IGNORE INTO `user_identity` (`username`, `authentication`) VALUES ('more', 'LDAP');
INSERT IGNORE INTO `user_identity` (`username`, `authentication`) VALUES ('less', 'LDAP');
INSERT IGNORE INTO `user_identity_role` 
  SELECT `user_identity_id`, `user_role_id`
    FROM `user_identity`, `user_role` 
    WHERE `username` IN ('more', 'less') AND `name` = 'ROLE_OMNI_ADMIN';
