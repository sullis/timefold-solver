************************************************************************
file with basedata            : md376_.bas
initial value random generator: 714115960
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  22
horizon                       :  153
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     20      0       23        0       23
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          2          13  21
   3        3          2           6  14
   4        3          3           5   7   8
   5        3          3           6  13  15
   6        3          3           9  11  18
   7        3          3           9  10  15
   8        3          1          17
   9        3          1          19
  10        3          2          11  13
  11        3          3          12  19  20
  12        3          1          16
  13        3          1          17
  14        3          3          15  16  17
  15        3          2          18  21
  16        3          1          21
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
  2      1     7       3    6    4    8
         2    10       1    6    3    7
         3    10       2    5    2    7
  3      1     2       1    2    6    7
         2     9       1    2    1    6
         3     9       1    2    2    5
  4      1     1       3    8    8    6
         2     7       2    5    8    5
         3     8       1    4    7    4
  5      1     5       8    9    8    7
         2     7       8    6    7    6
         3     8       7    4    6    4
  6      1     4       4    8    9    8
         2     7       3    5    8    6
         3     9       3    3    7    2
  7      1     1       7    4    8    1
         2     5       6    4    8    1
         3     6       4    3    7    1
  8      1     6       7    4    4    9
         2     8       7    4    2    5
         3     9       7    4    2    3
  9      1     3       3    4    6    8
         2     4       3    3    6    7
         3     5       2    3    6    6
 10      1     6       8    5    9    6
         2     9       6    3    9    6
         3    10       4    1    8    6
 11      1     1       7    8    2    7
         2     4       6    6    2    6
         3     5       6    6    1    6
 12      1     5      10    7    7    8
         2     8       9    6    7    6
         3    10       9    4    7    4
 13      1     2       9    7    8    5
         2    10       7    6    4    5
         3    10       6    6    5    5
 14      1     1       8   10    7    8
         2     3       8    7    3    8
         3     9       7    5    3    8
 15      1     4       6    4    5    8
         2     6       4    2    5    8
         3    10       1    2    3    6
 16      1     1       4    9   10    6
         2     1       5    6    6    6
         3     7       2    2    5    5
 17      1     3       7    7   10    6
         2     4       6    5    7    4
         3     7       6    4    7    2
 18      1     9       5   10    4    7
         2     9       6    3    8    3
         3     9       5    6    7    7
 19      1     1       4    6    6    9
         2     2       4    5    3    7
         3     2       3    5    4    8
 20      1     1       8    8    2    7
         2     4       8    7    2    7
         3     6       8    4    2    6
 21      1     1       7    7   10    3
         2     3       7    7    8    3
         3     4       5    6    6    2
 22      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   34   34  126  124
************************************************************************