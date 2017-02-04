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

- (void)didSave {
    
    NSString *bundleIdentifier = [NSBundle mainBundle].bundleIdentifier;
    UICKeyChainStore *keychain = [UICKeyChainStore keyChainStoreWithService:bundleIdentifier];
    [keychain setString:self.ownerToken forKey:self.identifier];
    
    [super didSave];
}

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
