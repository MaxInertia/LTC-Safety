//
//  LTCClientApi.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCClientApi.h"

NSString * const LTCNetworkError = @"NETWORK_ERROR";

@interface LTCClientApi ()
@property (nonatomic, strong) GTLRClientService *service;
@end

@implementation LTCClientApi

/**
 Constructor for the LTCClientApi that defines the service to be a newly allocated GTLRClientService.

 @return The LTCClientApi
 */
- (instancetype)init {
    if (self = [super init]) {
        self.service = [[GTLRClientService alloc] init];
    }
    return self;
}

- (void)submitConcern:(GTLRClient_ConcernData *)concern completion:(LTCSubmitConcernCompletion)completion {

    NSAssert(concern != nil, @"Attempted to submit a nil concern");
    NSAssert(completion != nil, @"Attempted to submit a concern with a nil completion block");
    
    GTLRClientQuery_SubmitConcern *query = [GTLRClientQuery_SubmitConcern queryWithObject:concern];
    [self.service executeQuery:query completionHandler:^(GTLRServiceTicket *ticket, id object, NSError *error) {
        
        if(error.code == -1009){
            NSMutableDictionary* details = [NSMutableDictionary dictionary];
            [details setValue:NSLocalizedString(LTCNetworkError, nil) forKey:@"error"];
            error = [NSError errorWithDomain:@"networkError" code:-1009 userInfo:details];
        }
        
        completion(object, error);
    }];
}

- (void)retractConcern:(NSString *)ownerToken completion:(LTCRetractConcernCompletion)completion {
 
    NSAssert(ownerToken != nil, @"Attempted to retract a concern with a nil owner token");
    NSAssert(completion != nil, @"Attempted to retract a concern with a nil completion block");
    
    GTLRClient_OwnerToken *clientToken = [[GTLRClient_OwnerToken alloc] init];
    clientToken.token = ownerToken;
    
    GTLRClientQuery_RetractConcern *query = [GTLRClientQuery_RetractConcern queryWithObject:clientToken];
    [self.service executeQuery:query completionHandler:^(GTLRServiceTicket *ticket, id object, NSError *error) {
        
        if(error.code == -1009){
            NSMutableDictionary* details = [NSMutableDictionary dictionary];
            [details setValue:NSLocalizedString(LTCNetworkError, nil) forKey:@"error"];
            error = [NSError errorWithDomain:@"networkError" code:-1009 userInfo:details];
        }
        
        completion(object, error);
    }];
}

- (void)fetchConcerns:(GTLRClient_OwnerTokenListWrapper *)inputTokenWrapper completion:(LTCFetchConcernsCompletion)completion {
    
    NSAssert(inputTokenWrapper != nil, @"Attempted to fetch a collection of concerns with a nil tokenWrapper");
    NSAssert(completion != nil, @"Attempted to fetch a collection of concerns with a nil completion block");
    
    GTLRClientQuery_FetchConcerns *query = [GTLRClientQuery_FetchConcerns queryWithObject:inputTokenWrapper];
    [self.service executeQuery:query completionHandler:^(GTLRServiceTicket *ticket, id object, NSError *error) {
        
        if(error.code == -1009){
            NSMutableDictionary* details = [NSMutableDictionary dictionary];
            [details setValue:NSLocalizedString(LTCNetworkError, nil) forKey:@"error"];
            error = [NSError errorWithDomain:@"networkError" code:-1009 userInfo:details];
        }
        
        completion(object, error);
    }];
}

@end
