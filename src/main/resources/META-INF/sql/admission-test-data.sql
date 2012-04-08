INSERT IGNORE INTO `user_identity` (`username`, `authentication`) VALUES ('more', 'LDAP');
INSERT IGNORE INTO `user_role` VALUES (1,'ROLE_EXAMINER'),(2,'ROLE_SUPERVISOR');
INSERT IGNORE INTO `user_permission` VALUES (1,'PERM_READ_PERSON'),(3,'PERM_WRITE_PHOTO'),(2,'PERM_WRITE_RESULT');

INSERT IGNORE INTO `user_identity_role` VALUES (1,2);
INSERT IGNORE INTO `user_role_permission` VALUES (1,1),(1,2),(1,3),(2,1),(2,2),(2,3);
