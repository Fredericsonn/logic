def EQUIVALENCE(p, q) :
    evaluation = [0,0,0,0]
    for i in range [3] :
        if p[i] == q[i] :
            evaluation[i] = 1
    return evaluation

def IMPLICATION(p, q) :
    evaluation = [0,0,0,0]
    for i in range [3] :
        if p[i] == 0 :
            evaluation[i] = 1
        elif q[i] == 1 :
            evaluation[i] = 1
    return evaluation

def NEGATION(p) :
    evaluation = [0,0,0,0]
    for i in range [3] :
        if p[i] == 0 :
            evaluation[i] = 1
    return evaluation

def AND(p, q) :
    evaluation = [0,0,0,0]
    for i in range [3] :
        if p[i] == q[i] == 1 :
            evaluation[i] = 1
    return evaluation

def OR(p, q) :
    evaluation = [0,0,0,0]
    for i in range [3] :
        if p[i] == 1 or q[i] == 1 :
            evaluation[i] = 1
    return evaluation

def evaluate(p,q,o) :
    if o == 'equivalence' :
        return EQUIVALENCE(p,q)
    elif o == 'implication' :
        return IMPLICATION(p, q)
    elif o == 'negation' :
        return NEGATION(p, q)
    elif o == 'and' :
        return AND(p, q)
    elif o == 'or' :
        return OR(p, q)
    
def isTautology(state) :
    for s in state :
        if s == 0 :
            return False
    return True

def isContradiction(state) :
    for s in state :
        if s == 1 :
            return False
    return True

def deduct(state) :
    if isTautology(state) :
        return "Tautology"
    elif isContradiction(state) :
        return "Contradiction"
    else :
        return "Indetermine"