%% -------------------------------------- Objective 4 -------------------------------------- 
clear;
sizes = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
% ************************************* QuickSelect ************************************* 
QuickSelect_times = [1.0E-4, 1.0E-4, 1.0E-4, 4.0E-4, 2.0E-4, 2.0E-4, 1.0E-4, 1.0E-4, 2.0E-4, 1.0E-4];
QuickSelect_comparisons = [0.0, 1.0, 2.6647, 4.1662, 7.2811, 9.0148, 13.554, 15.709, 19.6743, 22.2317];
% ************************************* InsertionSort ************************************* 
InsertionSort_times = [0.0, 2.0E-4, 1.0E-4, 2.0E-4, 6.0E-4, 4.0E-4, 6.0E-4, 2.0E-4, 3.0E-4, 2.0E-4];
InsertionSort_comparisons = [0.0, 1.0, 3.0, 5.3382, 8.9996, 11.7627, 16.9847, 20.2027, 26.9641, 30.7027];
%% -------------------------------------- Summary -------------------------------------- 
data = [sizes; QuickSelect_times; InsertionSort_times; QuickSelect_comparisons; InsertionSort_comparisons; ];
plot(sizes, QuickSelect_comparisons-InsertionSort_comparisons, '-xb');grid on;
