
%% times
plot(sizes, times, '-ob');

%% comparisons
plot(sizes, comparisons, '-ob');

%% postprocess
hold on;
grid on;
ax = gca;
ax.XScale = 'log';
ax.YScale = 'log';