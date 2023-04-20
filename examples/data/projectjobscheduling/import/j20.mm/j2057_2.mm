************************************************************************
file with basedata            : md377_.bas
initial value random generator: 1003282775
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  22
horizon                       :  144
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     20      0       20        9       20
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3           5   9  13
   3        3          3           6   9  17
   4        3          3          10  12  19
   5        3          3           7  11  14
   6        3          3           8  15  21
   7        3          1          21
   8        3          3          12  13  14
   9        3          2          16  21
  10        3          1          15
  11        3          3          12  16  17
  12        3          1          18
  13        3          1          20
  14        3          1          16
  15        3          1          18
  16        3          2          19  20
  17        3          2          18  19
  18        3          1          20
  19        3          1          22
  20        3          1          22
  21        3          1          22
  22        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     1       0    9   10    7
         2     5       0    7    9    7
         3    10       0    7    9    4
  3      1     2       5    0    8    8
         2     4       0    3    3    7
         3    10       0    1    3    5
  4      1     5      10    0    3    6
         2     7       0    6    3    5
         3     9       7    0    2    4
  5      1     6       4    0   10    2
         2     7       4    0    7    2
         3    10       0    9    7    2
  6      1     1       0    9    3    8
         2     3       2    0    2    8
         3     4       2    0    1    7
  7      1     2       0    4    8    9
         2     2       0    5    8    8
         3     4       0    4    6    4
  8      1     1       8    0   10    4
         2     4       7    0    7    3
         3     8       2    0    3    2
  9      1     3       6    0    6    9
         2     4       0    7    4    7
         3    10       0    5    4    3
 10      1     4       8    0    6    4
         2     7       6    0    5    4
         3     8       6    0    3    3
 11      1     4       0    8   10    4
         2     7       3    0    4    3
         3     7       0    7    5    2
 12      1     1       0    9    8    3
         2     2       7    0    8    2
         3     3       4    0    7    2
 13      1     7       0    5    9    5
         2     7       0    5   10    4
         3     9       3    0    7    2
 14      1     1      10    0    6    6
         2     2       6    0    5    6
         3     2       0    5    5    4
 15      1     7       0    5    9    7
         2     9       0    4    3    7
         3     9       6    0    5    7
 16      1     1       7    0    6    4
         2     3       0    1    4    3
         3     6       2    0    2    3
 17      1     6       0    6    8    6
         2     8       7    0    8    6
         3     9       5    0    8    2
 18      1     1       0    4    9    4
         2     3       0    4    6    4
         3     5       0    3    5    3
 19      1     1       7    0    8    7
         2     2       5    0    6    4
         3     3       4    0    5    4
 20      1     2       0    9    6    5
         2    10       4    0    1    2
         3    10       0    9    1    2
 21      1     1      10    0    2    8
         2     3      10    0    2    7
         3     8       9    0    1    7
 22      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   14   13  146  116
************************************************************************