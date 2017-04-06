//
//  LTCReporter+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
#import "GTLRClient.h"

@class LTCConcern;

NS_ASSUME_NONNULL_BEGIN
/**
 The LTC Reporter class will represent a concern's reporter name, phone number, and email address.
 
 History properties: Properties of this class should not vary from the time they are created.
 
 Invariance properties: This class makes no assumptions.
 
 */
@interface LTCReporter : NSManagedObject
/**
 Creates a new concern reporter given a datastore concern reporter object and a context to save the reporter into.
 @pre           The data must be non-nil.
 @pre           The context must be non-nil.
 @param data    The datastore concern reporter data that will be used to create the local concern reporter.
 @param context The context that the local concern reporter will be saved into.
 
 @return The local concern reporter.
 */
+ (instancetype)reporterWithData:(nonnull GTLRClient_Reporter *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCReporter+CoreDataProperties.h"
