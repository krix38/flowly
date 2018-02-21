# Flowly

[![Build Status](https://travis-ci.org/krix38/flowly.svg?branch=master)](https://travis-ci.org/krix38/flowly) [![Coverage Status](https://coveralls.io/repos/github/krix38/flowly/badge.svg?branch=master&refreshBadge=2)](https://coveralls.io/github/krix38/flowly?branch=master)


Library for designing and running synchronous flow of task executions on provided model.

## Prerequisites
-Java 8

-Maven

## Installation

Inside cloned/downloaded repo run<code>
mvn clean install
</code>
and include dependency in your pom project

```xml
    <dependency>
      <groupId>com.github.krix38</groupId>
      <artifactId>flowly</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
```

## Usage



Flowly lets you create and schedule executions (actions) on given input model.

To make it work, yout input model has to extend <code>AbstractModel</code> class.

To create action you want to schedule, define class with method annoted by <code>@FlowAction</code> annotation. This method has to take one argument with model, which can be any object extending AbstractModel.

Action can be scheduled to run by <code>FlowRegister.register(Object)</code>. Registered actions are later run in order of <code>register(Object)</code> calls.

<code>FlowRegister.register(Object)</code> can take reference to existing action object or class of action object (in which case such class is instantiated before running <code>@FlowAction</code> method so such class needs to provide default constructor)

To run scheduled actions, call <code>FlowRegister.run(AbstractModel)</code> on your model class.

## Example

**Account.java (model)**
```java
public abstract class Account extends AbstractModel {
    private String username;

    public Account(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
```


**FreeAccount.java (model)**
```java
public class FreeAccount extends Account {
    public FreeAccount(String username, Integer daysLeft) {
        super(username);
        this.daysLeft = daysLeft;
    }
    private Integer daysLeft;

    public Integer getDaysLeft() {
        return daysLeft;
    }
}
```


**PremiumAccount.java (model)**
```java
public class PremiumAccount extends Account{

    public PremiumAccount(String username, Long availableStorage) {
        super(username);
        this.setAvailableStorage(availableStorage);
    }

    private Long availableStorage;

    public void setAvailableStorage(Long availableStorage) {
        this.availableStorage = availableStorage;
    }

    public Long getAvailableStorage() {
        return availableStorage;
    }
}
```


**AddStorage.java (action)**
```java
public class AddStorage {

    private Long storageToAdd;

    public AddStorage(Long storageToAdd) {
        this.storageToAdd = storageToAdd;
    }

    @FlowAction
    public void add(PremiumAccount premiumAccount){
        premiumAccount.setAvailableStorage(premiumAccount.getAvailableStorage() + storageToAdd);
    }
}
```


**MapFreeToPremiumAccount.java (action)**
```java
public class MapFreeToPremiumAccount {

    private static final Long DEFAULT_AVAILABLE_STORAGE = 100L;

    @FlowAction
    public PremiumAccount map(FreeAccount freeAccount){
        return new PremiumAccount(freeAccount.getUsername(), DEFAULT_AVAILABLE_STORAGE);
    }
}
```


**UpdateToPremiumService.java**
```java
public class UpdateToPremiumService {
    private FlowRegister flowRegister;

    public UpdateToPremiumService() {
        flowRegister = new FlowRegister();
        flowRegister.register(new MapFreeToPremiumAccount());  //can pass MapFreeToPremiumAccount.class as well
        flowRegister.register(new AddStorage(200L));
    }

    public List<PremiumAccount> updateToPremium(List<FreeAccount> freeAccounts) throws ActionExecutionException {
        return flowRegister.runForAll(freeAccounts)
                .stream()
                .filter(account -> !account.hasFailed())
                .map(account -> (PremiumAccount)account)
                .collect(Collectors.toList());
    }
}
```


For more examples take a look at [unit tests](https://github.com/krix38/flowly/blob/master/src/test/java/com/github/krix38/flowly/FlowlyRunnerTest.java)
