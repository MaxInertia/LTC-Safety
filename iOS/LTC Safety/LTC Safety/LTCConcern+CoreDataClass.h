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

@interface LTCConcern : NSManagedObject
@property (nonatomic, copy) NSString *ownerToken;
+ (instancetype)concernWithData:(nonnull GTLRClient_Concern *)data ownerToken:(NSString *)ownerToken inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCConcern+CoreDataProperties.h"
