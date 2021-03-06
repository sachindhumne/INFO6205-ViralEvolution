package geneticAlgorithm;

import config.Constant;
import simulation.PopulationGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
This file contains Genetic Algorithm steps like selection, crossover, mutation
for selecting the fittest individual
 */
public class GeneticAlgorithm {
    final List<Integer> generationFitnessList = new ArrayList<>();
    public VirusPopulation virusPopulation = new VirusPopulation();
    private Virus fittest;
    private Virus secondFittest;
    private int generationCount = 0;

    public GeneticAlgorithm() {

    }

    //Selection
    void selection() {

        //Select the fittest virus
        fittest = virusPopulation.getFittest();

        //Select the second-fittest virus
        secondFittest = virusPopulation.getSecondFittest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(virusPopulation.viruses[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            char temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;
        }

    }

    //Mutation
    void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(virusPopulation.viruses[0].geneLength);

        //Flip values at the mutation point
        if (fittest.genes[mutationPoint] == 71) {
            fittest.genes[mutationPoint] = 'G';
        } else {
            fittest.genes[mutationPoint] = 'C';
        }

        mutationPoint = rn.nextInt(virusPopulation.viruses[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 65) {
            secondFittest.genes[mutationPoint] = 'T';
        } else {
            secondFittest.genes[mutationPoint] = 'A';
        }
    }

    //Get fittest offspring
    Virus getFittestOffspring() {
        if (fittest.getFitness() > secondFittest.getFitness()) {
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest virus from most fittest offspring
    void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Get index of least fit virus
        int leastFittestIndex = virusPopulation.getLeastFittestIndex();

        //Replace least fittest virus from most fittest offspring
        virusPopulation.viruses[leastFittestIndex] = getFittestOffspring();
    }


    public int getGenerationCount() {
        return generationCount;
    }

    public Virus runGA(Virus previousGen, PopulationGraph populationGraph, int variantNumber) {

        Random rn = new Random();

        int gaFitness = previousGen != null ? previousGen.getFitness() : Constant.virusFitness;

        //Initialize host population
        virusPopulation.initializePopulation();

        //Calculate fitness of each virus
        virusPopulation.calculateFitness();

        System.out.println("Generation: " + generationCount + " Fittest: " + virusPopulation.fittest);
        generationFitnessList.add(virusPopulation.fittest);
        //While population gets a virus with maximum fitness
        while (virusPopulation.fittest < gaFitness) {
            ++generationCount;

            //Do selection
            selection();

            //Do cross over
            crossover();

            //Do mutation under a  probability
            if (rn.nextInt() % 30000 < 3) {
                mutation();
            }

            //Add the fittest offspring to population
            addFittestOffspring();

            //Calculate new fitness value
            virusPopulation.calculateFitness();
            generationFitnessList.add(generationCount, virusPopulation.fittest);

            System.out.println("Generation: " + generationCount + " Fittest: " + virusPopulation.fittest);
        }

        if (variantNumber == 1) {
            populationGraph.showGenerationFitnessGraphForFirstVariant(new ArrayList<>(generationFitnessList));
        } else if (variantNumber == 2) {
            populationGraph.showGenerationFitnessGraphForSecondVariant(new ArrayList<>(generationFitnessList));
        } else {
            populationGraph.showDeltaVariant(new ArrayList<>(generationFitnessList));
        }

        System.out.println("\nSolution found in generation: " + generationCount);
        System.out.println("Fitness: " + virusPopulation.getFittest().getFitness());
        System.out.print("Genes: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(virusPopulation.getFittest().genes[i]);
        }

        System.out.println();

        generationFitnessList.clear();
        generationCount = 0;
        return virusPopulation.getFittest();
    }
}
