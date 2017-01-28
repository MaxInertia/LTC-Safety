//
//  AppDelegate.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "AppDelegate.h"

@interface AppDelegate () <UISplitViewControllerDelegate>

@end

@implementation AppDelegate
@synthesize persistentContainer = _persistentContainer;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    // Setup the split view controller delegate and set the LTCConcernViewController's view model
    return YES;
}

#pragma mark - Core Data stack

- (NSPersistentContainer *)persistentContainer {
    return nil;
}

#pragma mark - Core Data Saving support

- (void)saveContext {
    // Save the managed object context
}

@end
