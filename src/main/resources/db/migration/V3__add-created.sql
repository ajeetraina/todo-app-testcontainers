alter table todos add column created_at timestamp not null default now();
alter table todos add column ragged_estimate varchar(256);