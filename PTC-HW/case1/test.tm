; 1^mx1^n=1^{m*n}

; the finite set of states
#Q = {l1,l2,l3,l4,l5,l6,r1,r2,r3,r4,r5,r6,r7,ac,w2,w3,w4,w5,w6,mv}

; check 1*x1*=1*

beforeX 1 1 r beforeX
beforeX x x r afterX
beforeX * * * err

afterX 1 1 r afterX
afterX = = r after=
beforeX * * * err

after= 1 1 r after=
after= _ _ l checkRight
after= * * * err

checkRight * * l checkRight
checkRight _ _ r checkExit


checkExit * * * d1l


; delete 1 from left

d1l 1 _ r Rx
d1l x x * finishMul

; move right to the =

R= 1 1 r R=
R= = = l dMul

; simulate the multiply

dMul 1 a r Rm
dMul a a l dMul
dMul x x r recover
; aaaaa->11111
recover a 1 r recover
recover = = l Lm
; move to the leftmost
Lm * * l Lm
Lm _ _ r d1l

; move to the rightmost and delete 1 from the right

Rm * * r Rm
Rm _ _ l d1r

d1r 1 _ l L=
d1r = = * err

; move to the next 1 at the RHS of x
L= 1 1 l L=
L= = = l dMul

finishMul * * *

finalCheck x _ r finalCheck
finalCheck 1 _ r finalCheck
finalCheck = _ r isEmpty
isEmpty _ T r pR
pR _ r r pU
pU _ u r pE
pE _ e r ac

isEmpty * e * err

; move to the rightmost
err * * r err
; move to the leftmost
err _ _ l el
el * _ l el
el _ F r pA
pA _ a r pl
pl _ l r ps
ps _ s r pe
pe _ e r rej



; the finite set of input symbols
#S = {0,1,x,=}

; the complete set of tape symbols
#T = {0,1,_,T,r,u,e,F,a,l,s}

; the start state
#q0 = beforeX

; the blank symbol
#B = _

; the set of final states
#F = {ac}
