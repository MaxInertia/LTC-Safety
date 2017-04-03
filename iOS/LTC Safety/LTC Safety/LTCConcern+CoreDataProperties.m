//
//  LTCConcern+CoreDataProperties.m
//  
//
//  Created by Daniel Morris on 2017-03-20.
//
//

#import "LTCConcern+CoreDataProperties.h"

@implementation LTCConcern (CoreDataProperties)

+ (NSFetchRequest<LTCConcern *> *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCConcern"];
}

@dynamic actionsTaken;
@dynamic concernNature;
@dynamic descriptionProperty;
@dynamic identifier;
@dynamic ownerToken;
@dynamic submissionDate;
@dynamic location;
@dynamic reporter;
@dynamic statuses;

@end
