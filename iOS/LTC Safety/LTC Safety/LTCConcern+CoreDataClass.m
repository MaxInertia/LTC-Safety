//
//  LTCConcern+CoreDataClass.m
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import "LTCConcern+CoreDataClass.h"
#import "LTCConcernStatus+CoreDataClass.h"
#import "LTCLocation+CoreDataClass.h"
#import "LTCReporter+CoreDataClass.h"
#import "UICKeyChainStore.h"

@implementation LTCConcern

/**
 Called when the concern is about to be saved. Allows for setup of the concern.
 */
- (void)didSave {
    
    NSString *bundleIdentifier = [NSBundle mainBundle].bundleIdentifier;
    UICKeyChainStore *keychain = [UICKeyChainStore keyChainStoreWithService:bundleIdentifier];
    [keychain setString:self.ownerToken forKey:self.identifier];
    
    [super didSave];
}

/**
 Called when the concern is about to be read from disk. Allows for setup of the concern.
 */
- (void)awakeFromFetch {
    
    [super awakeFromFetch];
    
    NSString *bundleIdentifier = [NSBundle mainBundle].bundleIdentifier;
    UICKeyChainStore *keychain = [UICKeyChainStore keyChainStoreWithService:bundleIdentifier];
    self.ownerToken = [keychain stringForKey:self.identifier];
}

+ (instancetype)concernWithData:(nonnull GTLRClient_Concern *)data ownerToken:(NSString *)ownerToken inManagedObjectContext:(nonnull NSManagedObjectContext *)context {
    
    NSAssert(data != nil, @"The concern data must be non-null");
    NSAssert(context != nil, @"The managed object context must be non-null");
    
    NSEntityDescription *description = [NSEntityDescription entityForName:@"LTCConcern" inManagedObjectContext:context];
    return [[LTCConcern alloc] initWithData:data ownerToken:ownerToken entity:description insertIntoManagedObjectContext:context];
}

/**
 Conctructs the concern object given data passed through the concernWithData method.
 
 @param concernData The datastore object that will be passed to create the local concern.
 @param ownerToken  The ownerToken to be saved into the local concern that allows the client to have access to the data once it is in the datastore.
 @param entity      The entity description for this object.
 @param context     The object space this concern will be stored in.

 @return The constructed LTCConcern
 */
- (instancetype)initWithData:(nonnull GTLRClient_Concern *)concernData ownerToken:(NSString *)ownerToken entity:(NSEntityDescription *)entity insertIntoManagedObjectContext:(NSManagedObjectContext *)context {
    if (self = [super initWithEntity:entity insertIntoManagedObjectContext:context]) {
        
        NSLog(@"%@", self.class);
        self.ownerToken = ownerToken;
        self.identifier = [concernData.identifier stringValue];
        self.submissionDate = concernData.submissionDate.date;
        self.actionsTaken = concernData.data.actionsTaken;
        self.concernNature = concernData.data.concernNature;
        
        self.reporter = [LTCReporter reporterWithData:concernData.data.reporter inManagedObjectContext:context];
        self.location = [LTCLocation locationWithData:concernData.data.location inManagedObjectContext:context];
        
        NSMutableOrderedSet *statuses = [NSMutableOrderedSet orderedSetWithCapacity:concernData.statuses.count];
        for (GTLRClient_ConcernStatus *statusData in concernData.statuses) {
            LTCConcernStatus *status = [LTCConcernStatus statusWithData:statusData inManagedObjectContext:context];
            [statuses addObject:status];
        }
        self.statuses = [statuses copy];
    }
    return self;
}

@end
