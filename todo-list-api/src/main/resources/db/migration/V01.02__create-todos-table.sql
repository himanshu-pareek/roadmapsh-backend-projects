create table todos (
    id bigserial primary key,
    title varchar (100) not null,
    description varchar (1000),
    completed boolean default false not null,
    owner varchar(50) not null,

    created_at timestamp with time zone default now() not null,
    updated_at timestamp with time zone default now() not null,

    constraint fk_todos_users foreign key (owner) references users (username)
);

create index ix_todos_owner on todos (owner);
