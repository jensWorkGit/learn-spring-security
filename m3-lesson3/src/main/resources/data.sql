-- Test User
insert ignore into User (id, email, password, enabled, created) values (1, 'test@email.com', 'pass', true, '2008-08-08 00:00:00');

create table if not exists persistent_logins (
  username varchar (64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);