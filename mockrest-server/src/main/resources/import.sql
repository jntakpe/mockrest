-- Authorities
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');

-- Users
INSERT INTO t_user (version, login, first_name, last_name, email, phone, temporary) VALUES (0, 'jntakpe','Jocelyn','Ntakpé','jntakpe@gmail.com',1234567890,true);
INSERT INTO t_user (version, login, first_name, last_name, email, phone, temporary) VALUES (0, 'test','Test','TEST','test@gmail.com',1234567890,true);

-- UsersAuthorities
INSERT INTO user_authority (user_id, authority_name) VALUES ((select id from t_user where login='jntakpe'), 'ROLE_ADMIN');
INSERT INTO user_authority (user_id, authority_name) VALUES ((select id from t_user where login='jntakpe'), 'ROLE_USER');
INSERT INTO user_authority (user_id, authority_name) VALUES ((select id from t_user where login='test'), 'ROLE_USER');

-- Api
INSERT INTO api(version, name, visibility) VALUES (0, 'GMap', 'PUBLIC');
INSERT INTO api(version, name, visibility) VALUES (0, 'Facebook', 'PUBLIC');
INSERT INTO api(version, name, visibility) VALUES (0, 'Twitter', 'PUBLIC');

-- UsersApi
INSERT INTO user_api(version, user_id, api_id, authority_name) VALUES (0, (select id from t_user where login='jntakpe'),
(select id from api where name='GMap'), 'ROLE_ADMIN');

INSERT INTO user_api(version, user_id, api_id, authority_name) VALUES (0, (select id from t_user where login='jntakpe'),
(select id from api where name='Facebook'), 'ROLE_USER');

INSERT INTO user_api(version, user_id, api_id, authority_name) VALUES (0, (select id from t_user where login='test'),
(select id from api where name='GMap'), 'ROLE_USER');
