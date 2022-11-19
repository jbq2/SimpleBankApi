USE simplebankapidb;

ALTER TABLE user_role
ADD UNIQUE (user_id, role_id);