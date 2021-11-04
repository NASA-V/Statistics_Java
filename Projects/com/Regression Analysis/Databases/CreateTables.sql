create table All_Series
(
    _id INT default 1,
    _X  DOUBLE,
    _Y  DOUBLE
);

-- auto-generated definition
create table Datalist
(
    Dataset_Name TEXT not null,
    "Group Name" INTEGER primary key autoincrement
);

-- auto-generated definition
create table Series_Information
(
    _id          INTEGER default 1
        primary key autoincrement,
    Column1_Name INTEGER,
    Column2_Name INTEGER,
    Series_Name  TEXT,
    "Group Name" INTEGER,
    Series_Title TEXT
);
