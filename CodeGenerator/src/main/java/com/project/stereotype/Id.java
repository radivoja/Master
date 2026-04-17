package com.project.stereotype;

public class Id extends Stereotype {
    private String generated;
    private String strategy;

    public String getGenerated() {
        return generated;
    }

    public void setGenerated(String generated) {
        this.generated = generated;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }


    @Override
    public String toString() {
        return "Id{" +
                "generated='" + generated + '\'' +
                ", generationStrategy='" + strategy + '\'' +
                '}';
    }
}
