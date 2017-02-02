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

@interface LTCConcern ()
// Store the owner token to keychain on save and awake from fetch
- (void)didSave;
- (void)awakeFromFetch;
@end

@implementation LTCConcern

+ (instancetype)concernWithData:(nonnull GTLRClient_Concern *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context {
    
    NSAssert(data != nil, @"The concern data must be non-null");
    NSAssert(context != nil, @"The managed object context must be non-null");
    
    NSEntityDescription *description = [NSEntityDescription entityForName:@"LTCConcern" inManagedObjectContext:context];
    return [[LTCConcern alloc] initWithData:data entity:description insertIntoManagedObjectContext:context];
}

- (instancetype)initWithData:(nonnull GTLRClient_Concern *)concernData entity:(NSEntityDescription *)entity insertIntoManagedObjectContext:(NSManagedObjectContext *)context {
    if (self = [super initWithEntity:entity insertIntoManagedObjectContext:context]) {
                
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
