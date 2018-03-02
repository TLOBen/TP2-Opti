set OBJECTS;

param weight {OBJECTS} > 0;
param volume {OBJECTS} > 0;
param value {OBJECTS} > 0;
param knapsackSize >= 0;
param knapsackVolume >= 0;
param knapsacks > 0 integer;

var TakeObject {1..knapsacks, OBJECTS} >= 0 integer;

maximize totalValue:
  sum {k in 1..knapsacks} sum {o in OBJECTS} value[o] * TakeObject[k, o];

subject to MaxOneObject {o in OBJECTS}:
  sum {k in 1..knapsacks} TakeObject[k, o] <= 1;

subject to knapsackMaxSize {k in 1..knapsacks}:
  sum {o in OBJECTS} weight[o] * TakeObject[k, o] <= knapsackSize;

subject to knapsackMaxVolume {k in 1..knapsacks}:
  sum {o in OBJECTS} volume[o] * TakeObject[k, o] <= knapsackVolume;