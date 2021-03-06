package geneticAlgorithm;

import org.junit.Assert;
import org.junit.Test;

public class VirusPopulationTest {

    @Test
    public void initializePopulationTest() {
        VirusPopulation virusPopulation = new VirusPopulation();
        virusPopulation.initializePopulation();
        Assert.assertTrue(virusPopulation.viruses.length > 0);
    }

    @Test
    public void testFittest() {
        VirusPopulation virusPopulation = new VirusPopulation();
        virusPopulation.initializePopulation();
        Assert.assertNotNull(virusPopulation.getFittest());
        Assert.assertTrue(virusPopulation.viruses.length > 0);
    }

    @Test
    public void testSecondFittest() {
        VirusPopulation virusPopulation = new VirusPopulation();
        virusPopulation.initializePopulation();
        Assert.assertNotNull(virusPopulation.getSecondFittest());
        Assert.assertTrue(virusPopulation.viruses.length > 0);
    }

    @Test
    public void testLeastFittest() {
        VirusPopulation virusPopulation = new VirusPopulation();
        virusPopulation.initializePopulation();
        Assert.assertTrue(virusPopulation.getLeastFittestIndex() > 0);
    }

    @Test
    public void calculateFitnessTest() {
        VirusPopulation virusPopulation = new VirusPopulation();
        virusPopulation.initializePopulation();
        virusPopulation.calculateFitness();
        Assert.assertTrue(virusPopulation.fittest > 65);
        Assert.assertTrue(virusPopulation.fittest < 850);
    }

    @Test
    public void compareFitnessTest() {
        VirusPopulation virusPopulation = new VirusPopulation();
        virusPopulation.initializePopulation();
        virusPopulation.calculateFitness();
        Assert.assertNotNull(virusPopulation.getFittest());
        Assert.assertNotNull(virusPopulation.getSecondFittest());
        Assert.assertTrue(virusPopulation.getSecondFittest().getFitness() < virusPopulation.getFittest().getFitness());
    }
}
