//
//  AppDelegate.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "AppDelegate.h"
#import "GTLRClient.h"

@interface AppDelegate () <UISplitViewControllerDelegate>

@end

@implementation AppDelegate
@synthesize persistentContainer = _persistentContainer;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    // Setup the split view controller delegate and set the LTCConcernViewController's view model
    return YES;
}


- (void)testConcernWithConcern {
    
    GTLRClient_Concern *concern = [[GTLRClient_Concern alloc] init];
    concern.archived = @NO;
    concern.identifier = @100000000;
    
    concern.submissionDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    
    GTLRClient_ConcernStatus *status = [[GTLRClient_ConcernStatus alloc] init];
    status.creationDate = [GTLRDateTime dateTimeWithDate:[NSDate date]];
    status.type = @"PENDING";
    concern.statuses = [NSArray arrayWithObject:status];

    GTLRClient_Reporter *reporter = [[GTLRClient_Reporter alloc] init];
    reporter.name = @"Name";
    reporter.phoneNumber = @"Phone number";
    reporter.email = @"Email";
    
    GTLRClient_Location *location = [[GTLRClient_Location alloc] init];
    location.facilityName = @"Facility";
    location.roomName = @"Room";
    
    GTLRClient_ConcernData *data = [[GTLRClient_ConcernData alloc] init];
    data.reporter = reporter;
    data.location = location;
    data.concernNature = @"Nature of concern";
    data.actionsTaken = @"Actions taken";
    
    concern.data = data;
    
    
    
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
