// NOTE: This file was generated by the ServiceGenerator.

// ----------------------------------------------------------------------------
// API:
//   User API (client/v1)
// Description:
//   This is an API

#import "GTLRClientQuery.h"

#import "GTLRClientObjects.h"

@implementation GTLRClientQuery

@dynamic fields;

@end

@implementation GTLRClientQuery_RetractConcern

+ (instancetype)queryWithObject:(GTLRClient_OwnerToken *)object {
  if (object == nil) {
    GTLR_DEBUG_ASSERT(object != nil, @"Got a nil object");
    return nil;
  }
  NSString *pathURITemplate = @"retract";
  GTLRClientQuery_RetractConcern *query =
    [[self alloc] initWithPathURITemplate:pathURITemplate
                               HTTPMethod:@"POST"
                       pathParameterNames:nil];
  query.bodyObject = object;
  query.loggingName = @"client.retractConcern";
  return query;
}

@end

@implementation GTLRClientQuery_SubmitConcern

+ (instancetype)queryWithObject:(GTLRClient_ConcernData *)object {
  if (object == nil) {
    GTLR_DEBUG_ASSERT(object != nil, @"Got a nil object");
    return nil;
  }
  NSString *pathURITemplate = @"submit";
  GTLRClientQuery_SubmitConcern *query =
    [[self alloc] initWithPathURITemplate:pathURITemplate
                               HTTPMethod:@"POST"
                       pathParameterNames:nil];
  query.bodyObject = object;
  query.expectedObjectClass = [GTLRClient_SubmitConcernResponse class];
  query.loggingName = @"client.submitConcern";
  return query;
}

@end
