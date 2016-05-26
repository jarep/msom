-- delete old data
delete from task;
delete from tasktype;

-- add few task types
insert into tasktype values(1, 10, 'Typ A');
insert into tasktype values(2, 15, 'Typ B');
insert into tasktype values(3, 10, 'Typ C');
insert into tasktype values(4, 5, 'Typ D');

-- add few tasks
instert into task values(1, 'Task1', 1);
instert into task values(2, 'Task2', 1);
instert into task values(3, 'Task3', 2);
instert into task values(4, 'Task4', 1);
instert into task values(5, 'Task5', 4);

