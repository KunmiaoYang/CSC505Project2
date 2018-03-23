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
plot(sizes, times, '-xb', sizes, Ct(1)*sizes+Ct(2), '-or');

%% comparison
plot(sizes, comparisons, '-xb', sizes, Cc(1)*sizes+Cc(2), '-or');

%% time-comparison
plot(comparisons, times, '-ob')

%% postprocess
grid on;
ax = gca;
% ax.XScale = 'log';
% ax.YScale = 'log';