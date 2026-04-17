package com.project.stereotype;

public class Id extends Stereotype {
    private String generated;
    private String generationStrategy;

    public String getGenerated() {
        return generated;
    }

    public void setGenerated(String generated) {
        this.generated = generated;
    }

    public String getGenerationStrategy() {
        return generationStrategy;
    }

    public void setGenerationStrategy(String generationStrategy) {
        this.generationStrategy = generationStrategy;
    }


    @Override
    public String toString() {
        return "Id{" +
                "generated='" + generated + '\'' +
                ", generationStrategy='" + generationStrategy + '\'' +
                '}';
    }
}
