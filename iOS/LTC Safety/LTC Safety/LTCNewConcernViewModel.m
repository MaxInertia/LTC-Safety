//
//  LTCNewConcernViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCNewConcernViewModel.h"
#import "LTCClientApi.h"

NSString *const LTCContactInfoTitle               = @"CONTACT_INFO_TITLE";
NSString *const LTCConcernInfoTitle               = @"CONCERN_INFO_TITLE";
NSString *const LTCActionsTakenTitle              = @"ACTIONS_TAKEN_TITLE";

NSString *const LTCReporterNamePrompt             = @"REPORTER_NAME_PROMPT";
NSString *const LTCPhoneNumberPrompt              = @"PHONE_NUMBER_PROMPT";
NSString *const LTCEmailAddressPrompt             = @"EMAIL_ADDRESS_PROMPT";
NSString *const LTCConcernNaturePrompt            = @"CONCERN_NATURE_PROMPT";
NSString *const LTCFacilityPrompt                 = @"FACILITY_PROMPT";
NSString *const LTCRoomNumberPrompt               = @"ROOM_NUMBER_PROMPT";
NSString *const LTCSubmitConcernPrompt            = @"SUBMIT_CONCERN_PROMPT";

NSString *const LTCDescriptorReporterName         = @"REPORTER_NAME";
NSString *const LTCDescriptorPhoneNumber          = @"PHONE_NUMBER";
NSString *const LTCDescriptorEmail                = @"EMAIL";
NSString *const LTCDescriptorConcernNature        = @"CONCERN_NATURE";
NSString *const LTCDescriptorFacilityName         = @"FACILITY_NAME";
NSString *const LTCDescriptorRoomNumber           = @"ROOM_NUMBER";
NSString *const LTCDescriptorActionsTaken         = @"ACTIONS_TAKEN";
NSString *const LTCDescriptorSubmitConcern        = @"SUBMIT_CONCERN";

@interface LTCNewConcernViewModel ()
@property (nonatomic, strong) LTCClientApi *clientApi;
@property (nonatomic, strong) NSManagedObjectContext *context;
@property (readonly, nonatomic, strong) NSArray <NSString *> *testHook_descriptors;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorReporterName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorPhoneNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorEmail;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorConcernNature;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorFacilityName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorRoomNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorActionsTaken;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorSubmitConcern;
@end

@implementation LTCNewConcernViewModel
@dynamic submissionCallback;
@dynamic testHook_descriptors;
@dynamic testHook_descriptorReporterName;
@dynamic testHook_descriptorPhoneNumber;
@dynamic testHook_descriptorEmail;
@dynamic testHook_descriptorConcernNature;
@dynamic testHook_descriptorFacilityName;
@dynamic testHook_descriptorRoomNumber;
@dynamic testHook_descriptorActionsTaken;
@dynamic testHook_descriptorSubmitConcern;

#pragma mark - Test Hooks

- (NSString *)testHook_descriptorReporterName {
    return LTCDescriptorReporterName;
}

- (NSString *)testHook_descriptorPhoneNumber {
    return LTCDescriptorPhoneNumber;
}

- (NSString *)testHook_descriptorEmail {
    return LTCDescriptorEmail;
}

- (NSString *)testHook_descriptorConcernNature {
    return LTCDescriptorConcernNature;
}

- (NSString *)testHook_descriptorFacilityName {
    return LTCDescriptorFacilityName;
}

- (NSString *)testHook_descriptorRoomNumber {
    return LTCDescriptorRoomNumber;
}

- (NSString *)testHook_descriptorActionsTaken {
    return LTCDescriptorActionsTaken;
}

- (NSString *)testHook_descriptorSubmitConcern {
    return LTCDescriptorSubmitConcern;
}

- (NSArray <NSString *> *)testHook_descriptors {
    return @[LTCDescriptorReporterName, LTCDescriptorPhoneNumber, LTCDescriptorEmail, LTCDescriptorConcernNature, LTCDescriptorFacilityName, LTCDescriptorRoomNumber, LTCDescriptorActionsTaken, LTCDescriptorSubmitConcern];
}

#pragma mark - Implementation

- (SEL)submissionCallback {
    
    XLFormRowDescriptor *descriptor =  [self formRowWithTag:LTCDescriptorSubmitConcern];
    NSAssert1(descriptor != nil, @"Unable to find descriptor with tag %@", LTCDescriptorSubmitConcern);
    
    return descriptor.action.formSelector;
}

- (void)setSubmissionCallback:(SEL)submissionCallback {
    
    XLFormRowDescriptor *descriptor =  [self formRowWithTag:LTCDescriptorSubmitConcern];
    NSAssert1(descriptor != nil, @"Unable to find descriptor with tag %@", LTCDescriptorSubmitConcern);
    
    descriptor.action.formSelector = submissionCallback;
}

- (instancetype)initWithContext:(NSManagedObjectContext *)context {
    
    NSAssert(context != nil, @"Attempted to create new concern view model with a nil context.");
    
    if (self = [super init]) {
    
        self.clientApi = [[LTCClientApi alloc] init];
        self.context = context;
        
        XLFormSectionDescriptor *section;
        XLFormRowDescriptor *row;
        
        section = [XLFormSectionDescriptor formSection];
        section.title = NSLocalizedString(LTCContactInfoTitle, nil);
        [self addFormSection:section];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorReporterName rowType:XLFormRowDescriptorTypeName];
        [row.cellConfigAtConfigure setObject:NSLocalizedString(LTCReporterNamePrompt, nil) forKey:@"textField.placeholder"];
        row.required = YES;
        [section addFormRow:row];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorPhoneNumber rowType:XLFormRowDescriptorTypeName];
        [row.cellConfigAtConfigure setObject:NSLocalizedString(LTCPhoneNumberPrompt, nil) forKey:@"textField.placeholder"];
        [section addFormRow:row];

        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorEmail rowType:XLFormRowDescriptorTypeName];
        [row.cellConfigAtConfigure setObject:NSLocalizedString(LTCEmailAddressPrompt, nil) forKey:@"textField.placeholder"];
        [section addFormRow:row];
        
        section = [XLFormSectionDescriptor formSection];
        section.title = NSLocalizedString(LTCConcernInfoTitle, nil);
        [self addFormSection:section];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorConcernNature rowType:XLFormRowDescriptorTypeSelectorPush title:NSLocalizedString(LTCConcernNaturePrompt, nil)];
        [section addFormRow:row];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorFacilityName rowType:XLFormRowDescriptorTypeSelectorPush title:NSLocalizedString(LTCFacilityPrompt, nil)];
        [section addFormRow:row];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorRoomNumber rowType:XLFormRowDescriptorTypeName];
        [row.cellConfigAtConfigure setObject:NSLocalizedString(LTCRoomNumberPrompt, nil) forKey:@"textField.placeholder"];
        [section addFormRow:row];
        
        section = [XLFormSectionDescriptor formSection];
        section.title = NSLocalizedString(LTCActionsTakenTitle, nil);
        [self addFormSection:section];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorActionsTaken rowType:XLFormRowDescriptorTypeTextView];
        row.required = YES;
        [section addFormRow:row];
        
        section = [XLFormSectionDescriptor formSection];
        [self addFormSection:section];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:LTCDescriptorSubmitConcern rowType:XLFormRowDescriptorTypeButton title:NSLocalizedString(LTCSubmitConcernPrompt, nil)];
        [section addFormRow: row];
    }
    
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    for (NSString *tag in self.testHook_descriptors) {
        NSAssert1([self formRowWithTag:tag] != nil, @"Nil form row for tag:%@", tag);
    }
    NSAssert(self.clientApi != nil, @"Client API was nil after the new concern view model initializer finished.");
    NSAssert(self.context != nil, @"Managed object context was nil after the new concern view model initializer finished.");
    
    return self;
}

- (void)submitConcernData:(void (^)(LTCConcern *concern, NSError *error))completion {

    NSAssert(completion != nil, @"Attempted to submit concern data with a nil completion block.");
    
    GTLRClient_Reporter *reporter = [[GTLRClient_Reporter alloc] init];
    reporter.name = [self formRowWithTag:LTCDescriptorReporterName].value;
    reporter.phoneNumber = [self formRowWithTag:LTCDescriptorPhoneNumber].value;
    reporter.email = [self formRowWithTag:LTCDescriptorEmail].value;
    
    GTLRClient_Location *location = [[GTLRClient_Location alloc] init];
    location.facilityName = [self formRowWithTag:LTCDescriptorFacilityName].value;
    location.roomName = [self formRowWithTag:LTCDescriptorRoomNumber].value;
    
    GTLRClient_ConcernData *data = [[GTLRClient_ConcernData alloc] init];
    data.reporter = reporter;
    data.location = location;
    data.concernNature = [self formRowWithTag:LTCDescriptorConcernNature].value;
    data.actionsTaken = [self formRowWithTag:LTCDescriptorActionsTaken].value;
    
    [self.clientApi submitConcern:data completion:^(GTLRClient_SubmitConcernResponse *response, NSError *error){
        
        NSAssert2((response || error) && !(response &&  error), @"Unexpected response from submit concern:(%@, %@)", response, error);
        
        if (error != nil) {
            completion(nil, error);
        } else {
            NSString *ownerToken = response.ownerToken.token;
            LTCConcern *concern = [LTCConcern concernWithData:response.concern ownerToken:ownerToken inManagedObjectContext:self.context];
            completion(concern, error);
        }
    }];
}

@end
