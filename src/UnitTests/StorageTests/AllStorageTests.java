package UnitTests.StorageTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({StorageTestNothingLearned.class, StorageTestOneInstance.class, StorageTestTwoInstances.class})
public class AllStorageTests {

}