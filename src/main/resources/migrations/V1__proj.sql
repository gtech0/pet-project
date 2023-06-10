create table users
(
    id        uuid         not null
        primary key,
    full_name varchar(255) not null,
    login     varchar(255) not null,
    password  varchar(255) not null,
    role      varchar(255)
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['USER'::character varying, 'ADMIN'::character varying])::text[]))
);

alter table users
    owner to postgres;

create table personal_task
(
    completed   boolean,
    end_time    date,
    start_time  date,
    id          uuid         not null
        primary key,
    user_id     uuid
        constraint fket739hq9w4sq2fjgu7hvp3xb8
            references users,
    description varchar(255),
    name        varchar(255) not null
);

alter table personal_task
    owner to postgres;

create table goal
(
    completed     boolean,
    expected_time time(6),
    creation_time timestamp(6),
    id            uuid not null
        primary key,
    task_id       uuid
        constraint fkhy7eyhf4f3vvxr7sqdvr9jixl
            references personal_task,
    user_id       uuid
        constraint fkf70arauooy8e5a5egk8k69xdr
            references users,
    description   varchar(255),
    name          varchar(255),
    priority      varchar(255)
        constraint goal_priority_check
            check ((priority)::text = ANY
                   ((ARRAY ['URGENT_IMPORTANT'::character varying, 'URGENT_UNIMPORTANT'::character varying, 'NOT_URGENT_IMPORTANT'::character varying, 'NOT_URGENT_UNIMPORTANT'::character varying])::text[]))
);

alter table goal
    owner to postgres;

