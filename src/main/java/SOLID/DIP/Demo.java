package SOLID.DIP;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum Relationship {
    PARENT, CHILD, SIBLING
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

class Relationships implements RelationshipBrowser { // LOW LEVEL
    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream().filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }
}

class Research { // HIGH LEVEL
//    public Research(Relationships relationships) {
//        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
//        relations.stream().filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
//                .forEach(ch -> System.out.println("John has a child called: " + ch.getValue2().name));
//    }

    public Research(RelationshipBrowser browser) {
        List<Person> children = browser.findAllChildrenOf("John");
        children.forEach(v -> System.out.println("John has a child called: " + v.name));
    }
}

class Demo {
    public static void main(String[] args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research(relationships);
    }
}