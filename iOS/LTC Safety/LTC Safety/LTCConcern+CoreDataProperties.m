//
//  LTCConcern+CoreDataProperties.m
//  
//
//  Created by Allan Kerr on 2017-02-02.
//
//

#import "LTCConcern+CoreDataProperties.h"

@implementation LTCConcern (CoreDataProperties)

+ (NSFetchRequest *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCConcern"];
}

@dynamic actionsTaken;
@dynamic concernNature;
@dynamic identifier;
@dynamic submissionDate;
@dynamic ownerToken;
@dynamic location;
@dynamic reporter;
@dynamic statuses;

@end
