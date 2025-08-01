create table task (
    id uuid not null primary key,
    title varchar(75) not null,
    description varchar(255),
    status varchar(15) not null check ( status in ('PENDING', 'IN_PROGRESS', 'COMPLETED') ),
    creation_date timestamp not null,
    completed_date timestamp,
    user_id uuid not null,
    constraint fk_task_user_id foreign key (user_id) references users(id)
);