//
//  LTCConcern+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
#import "LTCConcernStatus+CoreDataClass.h"
#import "LTCLocation+CoreDataClass.h"
#import "LTCReporter+CoreDataClass.h"
#import "GTLRClient.h"

@class LTCConcernStatus, LTCLocation, LTCReporter;

NS_ASSUME_NONNULL_BEGIN


/**
 This class is an extension of the LTCConcern entity in the xcdatamodel. It is used to model concerns within the application and allows them to persistent when the managed object context is saved or loaded.
 */
@interface LTCConcern : NSManagedObject


/**
 Initializes a new concern and adds it to the managed object context.

 @param data The data used to initialize the concern as packaged by the backend server.
 @param ownerToken The JWT giving the client permission to retract the concern.
 @param context The application-wide managed object context that the concern will be added to.
 @return An concern that has been added to the app-wide managed object context populated with the data received from the backend server.
 */
+ (instancetype)concernWithData:(nonnull GTLRClient_Concern *)data ownerToken:(NSString *)ownerToken inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCConcern+CoreDataProperties.h"
