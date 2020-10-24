grammar Caxdr;

caxdr : c (a|d)+ r ;

c : 'c' ;
a : 'a' ;
d : 'd' #dBranch
  | 'x' #xBranch
  ;
r : 'r' ;
