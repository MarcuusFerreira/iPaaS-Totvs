alter table task add column status_ord integer;

update task
set status_ord = case status
    when 'PENDING' then 0
    when 'IN_PROGRESS' then 1
    when 'COMPLETED' then 2
    else null
end;

alter table task drop constraint if exists task_status_check;
alter table task drop column status;

alter table task rename column status_ord to status;

alter table task add constraint task_status_check check (status in (0, 1, 2));

alter table subtask add column status_ord integer;

update subtask
set status_ord = case status
                     when 'PENDING' then 0
                     when 'IN_PROGRESS' then 1
                     when 'COMPLETED' then 2
                     else null
    end;

alter table subtask drop constraint if exists subtask_status_check;
alter table subtask drop column status;

alter table subtask rename column status_ord to status;

alter table subtask add constraint subtask_status_check check (status in (0, 1, 2));