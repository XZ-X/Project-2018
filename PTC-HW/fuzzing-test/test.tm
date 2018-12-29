; This example program accepts all the string except null
; Input: a string of 0's and 1's

; the finite set of states
#Q = {l1,l2,l3,l4,l5,l6,r1,r2,r3,r4,r5,r6,r7,ac,w2,w3,w4,w5,w6,mv}

l1 * * l l2
l1 _ e l w2
w2 _ s l w3
w3 _ l l w4
w4 _ a l w5
w5 _ F * w6
l2 _ _ l l3
l3 _ _ l l4
l4 _ _ l l5
l5 _ _ l l6
l6 _ T r r2
r2 _ r r r3
r3 _ u r r4
r4 _ e r r5
r5 * _ r r6
r6 * _ r r7
r7 * _ r r7
r7 _ _ r mv
mv _ _ l ac




; the finite set of input symbols
#S = {0,1}

; the complete set of tape symbols
#T = {0,1,_,T,r,u,e,F,a,l,s}

; the start state
#q0 = l1

; the blank symbol
#B = _

; the set of final states
#F = {ac}
