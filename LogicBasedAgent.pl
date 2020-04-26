rows(3).
cols(3).
startPos(0,0).
thanosPos(2,2).
stone(0,1,1).
stone(0,2,2).
stoneIndecies([1,2]).

isNumber(N) :-
    N is 1.
isNumber(N) :-
    isNumber(X), N is X+1.

inList(Index,[H|_]) :-
    Index = H.
inList(Index,[_|T]) :-
    inList(Index,T).

append([], List, List).
append([Head|Tail], List, [Head|Rest]) :-
    append(Tail, List, Rest).

%Initial Situation
situation(X,Y,[],s0) :-
    startPos(X,Y).

%snap
situation(X,Y,List,result(snap,Situation)) :-
    situation(X,Y,List,Situation),
    stoneIndecies(List),
    thanosPos(X,Y).

%right updated
situation(PosX, PosY, List, result(right,Situation)) :-
    situation(PosX, OldY, List, Situation), 
    cols(Y), OldY<Y, PosY is OldY+1.

%left
situation(PosX, PosY, List, result(left, Situation)) :-
    situation(PosX,OldY, List, Situation),
    OldY>0, PosY is OldY-1.

%up
situation(PosX, PosY, List, result(up,Situation)) :-
    situation(OldX, PosY, List, Situation),
    OldX>0,
    PosX is OldX-1.

%down
situation(PosX, PosY, List, result(down,Situation)) :-
    situation(OldX, PosY, List, Situation),
    rows(X),
    OldX<X,
    PosX is OldX+1.

%collect
situation(X,Y,List,result(collect,Situation)) :-
    situation(X,Y,InitList,Situation),
    stone(X,Y,I),
    not(inList(I,InitList)), 
    append(InitList,[I],List).
    %List = [InitList|I].

snapped(S) :-
    isNumber(N),
    call_with_depth_limit(snap(S),N,R),
    not(R = depth_limit_exceeded).

snap(S) :-
    situation(_,_,_,result(snap,S1)), S=result(snap,S1).