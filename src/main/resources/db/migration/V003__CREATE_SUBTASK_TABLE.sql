create table subtask (
    id uuid not null primary key,
    title varchar(75) not null,
    description varchar(255),
    status varchar(15) not null check ( status in ('PENDING', 'IN_PROGRESS', 'COMPLETED') ),
    creation_date timestamp not null,
    completed_date timestamp,
    task_id uuid not null,
    constraint fk_subtask_task_id foreign key (task_id) references task (id)
);