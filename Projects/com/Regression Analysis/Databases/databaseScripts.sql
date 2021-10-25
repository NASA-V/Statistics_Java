INSERT INTO Datalist (Dataset_Name)
VALUES ('new datalist');

INSERT INTO datalist (Dataset_Name)
VALUES ('Another Dataset');
-- step 2
-- create new Series-- insert values into All_Series where Values(GroupName, XValue, YValue)
INSERT INTO All_Series
VALUES (1,
        123456789,
        987654321);
-- create new series information
-- 
INSERT INTO Series_Information (Column1_Name,
                                Column2_Name,
                                Series_Name,
                                Series_Title)
VALUES ('X_Axis',
        'Y_Axis',
        'Generic Series Name',
        'Generic Series Title');

-- complex join that shows information on all tables
--Dataset_Name, _X, _Y , "Group Name"
SELECT All_Series._X,
       All_Series._Y,
       Series_Information.Column1_Name,
       Series_Information.Column2_Name,
       Series_Information.[Group Name],
       Series_Information.Series_Name,
       Series_Information.Series_Title,
       Datalist.Dataset_Name
FROM All_Series-- All_Series
         INNER JOIN
     Series_Information ON All_Series._id = Series_Information._id
         INNER JOIN
     Datalist ON Series_Information.[Group Name] = Datalist.[Group Name];

SELECT All_Series._X,
       All_Series._Y,
       Series_Information.Column1_Name,
       Series_Information.Column2_Name,
       Series_Information.[Group Name],
       Series_Information.Series_Name,
       Series_Information.Series_Title,
       Datalist.Dataset_Name
FROM All_Series-- All_Series
         INNER JOIN
     Series_Information ON All_Series._id = Series_Information._id
         INNER JOIN
     Datalist ON Series_Information.[Group Name] = Datalist.[Group Name]
;


-- Select Tables

SELECT dl.Dataset_Name, dl."Group Name", si."Group Name"
FROM Datalist dl,
     Series_Information si;

select Dataset_Name, "Group Name"
from Datalist;
select *
from Datalist;
select Dataset_Name, "Group Name"
from Datalist;


SELECT *
FROM All_Series;

SELECT *
FROM Series_Information;

update Series_Information
set "Group Name" = 2
where "Group Name" = 1;

--DDL
-- auto-generated definition
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
    "Group Name" INTEGER
        primary key autoincrement
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


