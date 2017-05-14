import matplotlib.pyplot as plt


def plot_errors(network, training_errors, test_errors):
    colors = ['r', 'b']
    markers = ['x', 'o']

    training_size = len(training_errors)
    plt.scatter(range(training_size), training_errors, c=colors[0], marker=markers[0])

    test_size = len(test_errors)
    plt.scatter(range(test_size), test_errors, c=colors[1], marker=markers[1])

    plt.ylabel('Error')
    plt.xlabel('Epochs')
    plt.ylim([0, 0.1])

    hidden_layers = 2
    title = '{} HLayers: {}, eta: {}'.format(hidden_layers, [x.reduced_description() for x in network.layers[:hidden_layers]], network.eta)
    plt.title(title)
    plt.show()
