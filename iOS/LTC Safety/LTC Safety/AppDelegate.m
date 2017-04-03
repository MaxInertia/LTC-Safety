//
//  AppDelegate.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "AppDelegate.h"
#import "GTLRClient.h"
#import "LTCConcernViewController.h"
#import "LTCConcernDetailViewController.h"
#import "LTCPersistentContainer.h"
#import "LTCLogger.h"


@interface AppDelegate () <UISplitViewControllerDelegate>

/**
 The persistent container that manages the application-wide managed object context used for loading and saving concerns.
 */
@property (nonatomic, strong) LTCPersistentContainer *persistentContainer;
@end

@implementation AppDelegate

/**
 Performs setup for the application. This method is responsible for setting up the persistence container, app-wide navigation bar appearance and initializing the split view controller.
 */
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {

    [LTCLogger configure];
    [LTCLogger log:@"Setting up the application" level:kLTCLogLevelInfo];
    
    // Set up application wide persistence container and managed object context
    self.persistentContainer = [[LTCPersistentContainer alloc] initWithName:@"LTC_Safety"];
    NSAssert(self.persistentContainer != nil, @"Failed to initialize a persistent container.");
    
    // Set global navigation bar coloring
    UIColor *color = [UIColor colorWithRed:0.0 green:122.0/255.0 blue:1.0 alpha:1.0];
    UINavigationBar *appearance = [UINavigationBar appearance];
    [appearance setBarStyle:UIBarStyleBlack];
    [appearance setOpaque:YES];
    [appearance setBarTintColor:color];
    [appearance setTintColor:[UIColor whiteColor]];
    
    UISplitViewController *splitViewController = (UISplitViewController *)self.window.rootViewController;
    
    NSAssert1([splitViewController isKindOfClass:[UISplitViewController class]], @"Root view controller is of unexpected type: %@", [splitViewController class]);
    NSAssert1(splitViewController.delegate == nil, @"Attempted to set the delegate for a split view controller when it already exists: %@", splitViewController.delegate);
    NSAssert1(splitViewController.viewControllers.count == 2, @"Unexpected number of view controllers for split view: %@", splitViewController.viewControllers);
    
    splitViewController.delegate = self;
    
    UINavigationController *masterNavigationController = splitViewController.viewControllers[0];
    LTCConcernViewController *controller = (LTCConcernViewController *)masterNavigationController.topViewController;
    NSAssert1([controller isKindOfClass:[LTCConcernViewController class]], @"Master navigation controller is of unexpected type: %@", [controller class]);
    
    controller.viewModel = [[LTCConcernViewModel alloc] initWithContext:self.persistentContainer.viewContext];
    
    return YES;
}
/**
 Called when the application is going to terminate. This causes the persistent container to save all entities.
 */
- (void)applicationWillTerminate:(UIApplication *)application {

    
    NSManagedObjectContext *context = self.persistentContainer.viewContext;
    
    [LTCLogger log :@"Application has terminated" level:kLTCLogLevelInfo];
    NSAssert(context != nil, @"Application terminated with nil object context.");
    NSError *error = nil;
    if ([context hasChanges] && ![context save:&error]) {
        NSLog(@"Unresolved error %@, %@", error, error.userInfo);
    }
}

/**
 Determines whether the concern detail view controller should be displayed based on whether it has a non-nil concern.
 */
- (BOOL)splitViewController:(UISplitViewController *)splitViewController collapseSecondaryViewController:(UIViewController *)secondaryViewController ontoPrimaryViewController:(UIViewController *)primaryViewController {    
    return [secondaryViewController isKindOfClass:[UINavigationController class]] &&
    [[(UINavigationController *)secondaryViewController topViewController] isKindOfClass:[LTCConcernDetailViewController class]] &&
    ([(LTCConcernDetailViewController *)[(UINavigationController *)secondaryViewController topViewController] concern] == nil);
}

@end
