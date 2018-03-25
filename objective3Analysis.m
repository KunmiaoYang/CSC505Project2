
%% times
% plot(sizes, times, '-ob');

%% comparisons
plot(sizes, comparisons, '-or');

%% postprocess
hold on;
grid on;
ax = gca;
ax.XScale = 'log';
ax.YScale = 'log';
plot(sizes, 1.5*sizes, '-k');
plot(sizes, 0.8*sizes.^1.5, '-.k');
plot(sizes, 0.3.*sizes.^2, '--k');