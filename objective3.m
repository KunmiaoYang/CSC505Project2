%% -------------------------------------- Objective 3 -------------------------------------- 
clear;
sizes = [10, 12, 14, 16, 18, 20, 24, 28, 30, 32, 34, 36, 40, 44, 52, 60, 62, 64, 66, 68, 70, 72, 76, 80, 84, 88, 96, 104, 112, 120, 124, 126, 128, 132, 140, 148];
times = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.05, 0.05, 0.0, 0.0];
comparisons = [26.0, 25.0, 42.0, 51.0, 58.0, 62.0, 76.0, 80.0, 155.0, 163.0, 171.0, 177.0, 189.0, 200.0, 208.0, 223.0, 508.0, 518.0, 528.0, 536.0, 546.0, 552.0, 566.0, 575.0, 582.0, 587.0, 592.0, 588.0, 576.0, 589.0, 574.0, 1661.0, 1673.0, 1695.0, 1733.0, 1757.0];
%% -------------------------------------- Summary -------------------------------------- 
data = [sizes; times; comparisons];