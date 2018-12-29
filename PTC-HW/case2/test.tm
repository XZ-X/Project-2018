; 1^mx1^n=1^{m*n}

; the finite set of states
#Q = {q1,q2,ac,q3,pr,q4,ps,q5,err,pu,q6,q7,q8,el,q9,q11,pA,q10,pe,pE,halt_ac,rej,preAc,pl}

; find the middle of the input
q1 _ _ * ac
q1 a X r q2
q1 b Y r q2

q2 * * r q2
q2 _ _ l q3
q2 X a l q3
q2 Y b l q3
q3 a X l q4
q3 b Y l q4
q4 a a l q4
q4 b b l q4
q4 X a r q1
q4 Y b r q1

q1 Y Y l q8
q1 X X l q5

; Z means 'a symbol that has been considered'
; match the top b in the second string
; and then move the pointer one step right

q8 * * l q8
q8 _ _ r q9
q8 Z _ r q9
q9 b Z r q10
q10 * * r q10
q10 Y b r q11
; expected the letter b(Y) but found a(X)
q10 X X * err


q11 _ _ l preAc
preAc * _ l preAc
preAc _ _ * ac

q11 b Y l q8

q11 a X l q5

; match a

q5 * * l q5
q5 _ _ r q6
q5 Z Z r q6
q6 a Z r q7
q7 * * r q7
q7 X a r q11
q7 Y Y * err


ac _ T r pr
pr _ r r pu
pu _ u r pe
pe _ e * halt_ac

; move to the rightmost
err * * r err
; move to the leftmost
err _ _ l el
el * _ l el
el _ F r pA
pA _ a r pl
pl _ l r ps
ps _ s r pE
pE _ e r rej


; the finite set of input symbols
#S = {a,b}

; the complete set of tape symbols
#T = {a,b,X,Y,Z,T,r,u,e,F,a,l,s,_}

; the start state
#q0 = q1

; the blank symbol
#B = _

; the set of final states
#F = {halt_ac}
