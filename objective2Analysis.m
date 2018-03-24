%% data
scale = 1e5;
n = length(sizes);
A = [sizes/scale; ones(1,n)]';
Als = (A'*A)\A';
Ct = Als*times'.*[1/scale; 1];
Cc = Als*comparisons'.*[1/scale; 1];
Ct(1)
Cc(1)

%% time
regSizes = [1e5:1e5:1e7];
plot(sizes, times, 'xb', regSizes, Ct(1)*regSizes+Ct(2), '-b');

%% comparison
plot(sizes, comparisons, 'xb', sizes, Cc(1)*sizes+Cc(2), '-r');

%% time-comparison
plot(comparisons, times, '-ob')

%% postprocess
hold on;
grid on;
ax = gca;
% ax.XScale = 'log';
% ax.YScale = 'log';