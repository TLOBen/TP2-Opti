set OBJECTS;

param weight {OBJECTS} > 0;
param value {OBJECTS} > 0;
param knapsackSize;

var TakeObject {OBJECTS} >= 0 integer;

maximize totalValue: sum {o in OBJECTS} value[o] * TakeObject[o];

subject to MaxOneObject {o in OBJECTS}:
  TakeObject[o] <= 1;

subject to knapsackMaxSize:
  sum {o in OBJECTS} weight[o] * TakeObject[o] <= knapsackSize;