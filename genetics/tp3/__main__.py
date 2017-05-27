import numpy as np
from .algorithms.genetic import Genetic
from .models.characters import Character, Warrior, Archer, Defender, Assassin
from .models.items import Armour, Boots, Gloves, Helmet, Weapon
from .utils.parser import parse
from random import sample
from .utils.config import Config


def print_stats(population):
    fitness_list = []
    for character in population:
        fitness_list.append(character.fitness)

    avg_fitness = np.average(fitness_list)
    max_fitness = np.max(fitness_list)
    min_fitness = np.min(fitness_list)
    print("Avg fitness: {}".format(avg_fitness))
    print("Max fitness: {}".format(max_fitness))
    print("Min fitness: {}".format(min_fitness))


def generate_individuals(amount, items, population_class):
    population = []
    for _ in range(amount):
        individual = class_setter(population_class)
        individual.add_item(sample(items[0], 1)[0])
        individual.add_item(sample(items[1], 1)[0])
        individual.add_item(sample(items[2], 1)[0])
        individual.add_item(sample(items[3], 1)[0])
        individual.add_item(sample(items[4], 1)[0])
        individual.calculate_fitness()
        population.append(individual)
    return population


def class_setter(population_class):
    return {
        'warrior': Warrior(),
        'archer': Archer(),
        'defender': Defender(),
        'assassin': Assassin(),
    }[population_class]


def databases():
    weapons = list(map(lambda x: Weapon(int(x[0]), x[1], x[2], x[3], x[4], x[5]), parse("testdata/armas.tsv")[0]))
    boots = list(map(lambda x: Boots(int(x[0]), x[1], x[2], x[3], x[4], x[5]), parse("testdata/botas.tsv")[0]))
    helmets = list(map(lambda x: Helmet(int(x[0]), x[1], x[2], x[3], x[4], x[5]), parse("testdata/cascos.tsv")[0]))
    gloves = list(map(lambda x: Gloves(int(x[0]), x[1], x[2], x[3], x[4], x[5]), parse("testdata/guantes.tsv")[0]))
    armours = list(map(lambda x: Armour(int(x[0]), x[1], x[2], x[3], x[4], x[5]), parse("testdata/pecheras.tsv")[0]))
    return weapons, boots, helmets, gloves, armours


def main():
    config = Config("config.json")
    items = databases()
    population_size = config.population_size
    Character.set_special_modifiers(config.special_modifiers)
    population_class = config.population_class
    population = generate_individuals(population_size, items, population_class)
    experiment = Genetic(config, population, items)
    print(experiment)
    print(population[0].spawn())


if __name__ == "__main__":
    main()
